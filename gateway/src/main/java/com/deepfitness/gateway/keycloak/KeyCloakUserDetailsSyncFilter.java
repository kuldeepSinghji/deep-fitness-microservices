package com.deepfitness.gateway.keycloak;

import com.deepfitness.gateway.dto.UserRegisterRequest;
import com.deepfitness.gateway.service.UserDetailsService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserDetailsSyncFilter implements WebFilter {
    private final UserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        UserRegisterRequest userRegisterRequest = getUserDetails(token);
        if (userId == null) {
            userId = userRegisterRequest.getKeyCloakId();
        }
        log.info("Filter is calling **********");
        if(userId != null && token != null){
            String finalUserId = userId;
            return userDetailsService.validateUserDetails(userId)
                    .flatMap(exist->{
                        if(!exist){
                            log.info("User does not exists with userId: " + finalUserId);
                            if(userRegisterRequest != null){
                               return userDetailsService.registerUserDetails(userRegisterRequest)
                                       .then(Mono.empty());
                            }else{
                                return Mono.empty();
                            }
                        }else{
                            return Mono.empty();
                        }
                    }).then(Mono.defer( ()->{
                ServerHttpRequest mutateRequest = exchange.getRequest()
                        .mutate().header("X-User-Id",finalUserId)
                        .build();
                return chain.filter(exchange.mutate().request(mutateRequest).build());
            }));
        }

        return chain.filter(exchange);
    }

    private UserRegisterRequest getUserDetails(String token) {
        try {
            String filterToken = token.replace("Bearer","").trim();
            SignedJWT signedJWT = SignedJWT.parse(filterToken);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
            userRegisterRequest.setKeyCloakId(claimsSet.getStringClaim("sub"));
            userRegisterRequest.setEmail(claimsSet.getStringClaim("email"));
            userRegisterRequest.setPassword("111111");
            userRegisterRequest.setFirstName(claimsSet.getStringClaim("given_name"));
            userRegisterRequest.setLastName(claimsSet.getStringClaim("family_name"));
            log.info("checking cloak Id: " + userRegisterRequest.getKeyCloakId());
            return userRegisterRequest;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
