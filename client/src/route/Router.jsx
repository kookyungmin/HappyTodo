import { Route, Routes } from "react-router-dom";
import App from "../App.jsx";
import NotFoundPage from "../pages/NotFoundPage.jsx";
import TodoListPage from "../pages/TodoListPage.jsx";

export default function Router() {
    return (
        <Routes>
            <Route path={"/"} element={<App />}>
                <Route path={"/todo"} element={<TodoListPage />} />
            </Route>
            <Route path={"*"} element={<NotFoundPage />} />
        </Routes>
    )
}