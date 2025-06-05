import { BrowserRouter as Router, Routes, Route } from "react-router"
import UserRecommandations from "./components/ai-service/UserRecommandations"
import Home from "./components/Home";
import { useContext, useEffect, useState } from "react"
import { AuthContext } from "react-oauth2-code-pkce"
import { useDispatch } from "react-redux";
import { setCredentials } from "./store/authSlice";  
import DrawerAppBar from "./components/appbar/NavBar"; 
import Contact from "./components/Contact";
import About from "./components/About";
import "./App.css";
import NotFound from "./components/NotFound";


function App() {
  const {token, logIn, tokenData, logOut}:any = useContext(AuthContext);
  const dispatch = useDispatch();
  const [isAuthReady, setIsAuthReady] = useState(false);
  useEffect(() => {
    if(token){
      dispatch(setCredentials({token,user:tokenData}))
      setIsAuthReady(true);
    }
  }, [token,tokenData,dispatch]) 
  
  return (
    <>
    <Router>
      <DrawerAppBar logOut={logOut} token={token} logIn={logIn}/>
      <Routes>
        <Route path="/" element={<Home isAuthReady={isAuthReady}  logIn={logIn} />}></Route>
          <Route path="recommandations" element={<UserRecommandations/>} />
          <Route path="contact" element={<Contact />} />
          <Route path="about" element={<About />} />
          <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
    </>
  )
}

export default App
