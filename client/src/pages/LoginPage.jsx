import LoginForm from "../components/LoginForm.jsx";
import {useNavigate} from "react-router-dom";

export default function LoginPage() {
    const navigate = useNavigate();

    const loginSuccess = (user) => {
        navigate('/todo');
    };

    return (
        <div className={'flex justify-center mt-7'}>
            <LoginForm onSuccess={loginSuccess} onFailure={(error) => alert(error.errorMessage)}/>
        </div>
    )
};