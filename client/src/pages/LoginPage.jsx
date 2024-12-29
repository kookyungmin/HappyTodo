import LoginForm from "../components/LoginForm.jsx";
import {useNavigate} from "react-router-dom";
import {useContext} from "react";
import {UserContext} from "../context/UserContext.js";

export default function LoginPage() {
    const { dispatch } = useContext(UserContext);
    const navigate = useNavigate();

    const loginSuccess = (user) => {
        dispatch({ type: 'setUser', payload: user });
        navigate('/todo');
    };

    return (
        <div className={'flex justify-center mt-7'}>
            <LoginForm onSuccess={loginSuccess} onFailure={(error) => alert(error.errorMessage)}/>
        </div>
    )
};