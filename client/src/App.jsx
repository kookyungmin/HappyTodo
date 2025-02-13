import './App.css'
import TodoNavbar from "./components/TodoNavbar.jsx";
import { Outlet } from "react-router-dom";
import { useEffect, useReducer, useState } from "react";
import { getLoginUserAction } from "./service/SecurityService.js";
// import { UserContext } from "./context/UserContext.js";
import UserReducer from "./reducer/UserReducer.js";
import UserStore from "./store/UserStore.js";

function App() {
    // const [ loginUser, dispatch ] = useReducer(UserReducer, null);
    const { setUser } = UserStore();

    const getLoginUser = async () => {
        const { isError, data } = await getLoginUserAction();
        if (isError) {
            alert(data.errorMessage);
            return;
        }
        // dispatch({ type: "setUser", payload: data });
        setUser(data);
    };

    useEffect(() => {
        getLoginUser();
    }, []);

    return (
        <>
            <TodoNavbar />
            <Outlet />
        </>
        // <UserContext.Provider value={{ loginUser, dispatch }}>
        //     <TodoNavbar />
        //     <Outlet />
        // </UserContext.Provider>
    )
}

export default App
