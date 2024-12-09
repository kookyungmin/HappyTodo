import { fetchGet } from "./fetch.js";

const serverHost = import.meta.env.VITE_SERVER_HOST;
const TODO_API_URL = `${serverHost}/api/todo`

export const fetchGetTodoStatusList = () => {
    return fetchGet(`${TODO_API_URL}/status`);
};

export const fetchGetTodoList = (status) => {
    return fetchGet(`${TODO_API_URL}/domain?status=${status}`);
}
