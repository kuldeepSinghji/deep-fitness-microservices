
import ReactDOM from 'react-dom/client'

import { Provider } from 'react-redux'
import { store } from './store/store'
import authConfig  from "./authConfig"
import App from './App'
import { AuthProvider } from 'react-oauth2-code-pkce';

// As of React 18
const rootDiv:HTMLElement|null  = document.getElementById('root');
if(rootDiv){
  const root = ReactDOM.createRoot( rootDiv );
  root.render(
    <AuthProvider authConfig={authConfig}>
    <Provider store={store}>
      <App />
    </Provider>
    </AuthProvider>
  )
}