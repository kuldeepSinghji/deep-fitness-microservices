import type { TAuthConfig, TRefreshTokenExpiredEvent } from "react-oauth2-code-pkce";

const authConfig: TAuthConfig = {
  clientId: 'oauth-pkce-client',
  authorizationEndpoint: 'http://localhost:8999/realms/deepfitness-oauth/protocol/openid-connect/auth',
  tokenEndpoint: 'http://localhost:8999/realms/deepfitness-oauth/protocol/openid-connect/token',
  logoutEndpoint:'http://localhost:8999/realms/deepfitness-oauth/protocol/openid-connect/logout',
  logoutRedirect:'http://localhost:5173',
  redirectUri: 'http://localhost:5173',
  scope: 'openid',
  onRefreshTokenExpire: (event: TRefreshTokenExpiredEvent) => event.logIn(),
}

export default authConfig;