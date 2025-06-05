
import UserActivityForm from "./activity-service/UserActivityForm"; 
import MainPage from "./MainPage";

interface props {
  isAuthReady:boolean,
  logIn:any
}

const Home = (props:props) => { 
  return (
    <>
      {props.isAuthReady && <UserActivityForm/>}
      {!props.isAuthReady && <MainPage logIn={props.logIn} />}
    </>
  )
}

export default Home;