import './App.css'
import TodoNavbar from "./components/TodoNavbar.jsx";
import {Outlet, useNavigate, useParams, useSearchParams} from "react-router-dom";
import { useEffect, useReducer, useState } from "react";
import { getLoginUserAction } from "./service/SecurityService.js";
// import { UserContext } from "./context/UserContext.js";
import UserReducer from "./reducer/UserReducer.js";
import UserStore from "./store/UserStore.js";
import {APIProvider} from "@vis.gl/react-google-maps";
const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

function App() {
    // const [ loginUser, dispatch ] = useReducer(UserReducer, null);
    const [ searchParams, setSearchParams ] = useSearchParams();
    const { setUser } = UserStore();
    const navigate = useNavigate();

    const getLoginUser = async () => {
        if (searchParams?.get("atk")) {
            sessionStorage.setItem("atk", searchParams?.get("atk"));
            searchParams.delete("atk");
            setSearchParams(searchParams);
        }
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
            <APIProvider apiKey={apiKey}>
                <TodoNavbar />
                <Outlet />
            </APIProvider>
        </>
        // <UserContext.Provider value={{ loginUser, dispatch }}>
        //     <TodoNavbar />
        //     <Outlet />
        // </UserContext.Provider>
    )
}

export default App
