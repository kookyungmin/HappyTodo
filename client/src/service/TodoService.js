import { fetchGet } from "./fetch.js";

const serverHost = import.meta.env.VITE_SERVER_HOST;
const TODO_API_URL = `${serverHost}/api/todo`

export const getTodoStatusListAction = () => {
    return fetchGet(`${TODO_API_URL}/status`);
};

export const getTodoListAction = (status) => {
    return fetchGet(`${TODO_API_URL}/domain?status=${status}`);
}
