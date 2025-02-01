import LoginForm from "../components/LoginForm.jsx";
import {useNavigate} from "react-router-dom";
import UserStore from "../store/UserStore.js";
// import {useContext} from "react";
// import {UserContext} from "../context/UserContext.js";

export default function LoginPage() {
    // const { dispatch } = useContext(UserContext);
    const { setUser } = UserStore();
    const navigate = useNavigate();

    const loginSuccess = (user) => {
        // dispatch({ type: 'setUser', payload: user });
        setUser(user);
        navigate('/todo');
    };

    return (
        <div className={'flex justify-center mt-7'}>
            <LoginForm onSuccess={loginSuccess} onFailure={(error) => alert('로그인에 실패했습니다.')}/>
        </div>
    )
};