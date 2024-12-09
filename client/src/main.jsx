import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { BrowserRouter } from "react-router-dom";
import Router from "./route/Router.jsx";

createRoot(document.getElementById('root')).render(
  // <StrictMode>
      <BrowserRouter>
          <Router />
      </BrowserRouter>
  // </StrictMode>,
)
