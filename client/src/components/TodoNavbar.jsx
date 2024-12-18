import { Navbar, Dropdown, Avatar } from "flowbite-react";
import logo from '../assets/react.svg';
import { Link } from "react-router-dom";

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
                    <Dropdown.Header>
                        <span className="block text-sm">꾸리</span>
                    </Dropdown.Header>
                    <Dropdown.Item>Sign out</Dropdown.Item>
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