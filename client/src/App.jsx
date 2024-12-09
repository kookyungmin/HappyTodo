import './App.css'
import TodoNavbar from "./components/TodoNavbar.jsx";
import { Outlet } from "react-router-dom";

function App() {
    return (
        <>
            <TodoNavbar />
            <Outlet />
        </>
    )
}

export default App
