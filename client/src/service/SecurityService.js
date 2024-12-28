import {fetchGet, fetchPost} from "./fetch.js";

const serverHost = import.meta.env.VITE_SERVER_HOST;
const SECURITY_API_URL = `${serverHost}/api/security`

export const loginByEmailAndPassword = ({ email, password }) => {
    return fetchPost(`${SECURITY_API_URL}/login`, { email, password });
}

export const logout = () => {
    return fetchPost(`${SECURITY_API_URL}/logout`);
}