import { Navbar, Dropdown, Avatar } from "flowbite-react";
import logo from '../assets/react.svg';
import {Link, useNavigate} from "react-router-dom";
import { logoutAction } from "../service/SecurityService.js";
import { useContext } from "react";
import { UserContext } from "../context/UserContext.js";

const TodoNavLink = ({ to, text }) => {
    return (
        <Navbar.Link as={'div'}>
            <Link to={to}>
                {text}
            </Link>
        </Navbar.Link>
    )
}

export default function TodoNavbar() {
    const { loginUser, dispatch } = useContext(UserContext);
    const navigate = useNavigate();

    const logout = async () => {
        const { isError, data } = await logoutAction();
        if (isError) {
            alert(data.errorMessage);
            return;
        }
        alert('successful sign out!');
        dispatch({ type: 'setUser', payload: null })
        navigate('/login')
    };

    return (
        <Navbar fluid rounded>
            <Navbar.Brand href={"/"}>
                <img src={logo} className="mr-3 h-6 sm:h-9" alt="React Logo" />
                <span className="self-center whitespace-nowrap text-xl font-semibold dark:text-white">HappyTodo</span>
            </Navbar.Brand>
            <div className="flex md:order-2">
                <Dropdown
                    placement={'bottom-end'}
                    arrowIcon={false}
                    inline
                    label={
                        <Avatar alt="User settings" img="https://flowbite.com/docs/images/people/profile-picture-5.jpg" rounded />
                    }
                >
                    {loginUser?.id ?
                        <>
                            <Dropdown.Header>
                                <span className="block text-sm">{loginUser.name}</span>
                            </Dropdown.Header>
                            <Dropdown.Item onClick={logout}>Sign out</Dropdown.Item>
                        </>
                        :
                        <Dropdown.Item>
                            <Link to={'/login'}>
                                Sign in
                            </Link>
                        </Dropdown.Item>
                    }

                </Dropdown>
                <Navbar.Toggle />
            </div>
            <Navbar.Collapse>
                <TodoNavLink to={'/'} text={'Home'} />
                <TodoNavLink to={'/todo'} text={'Todo List'} />
                <TodoNavLink to={'/my-page'} text={'My Page'} />
            </Navbar.Collapse>
        </Navbar>
    )
}