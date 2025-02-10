const serverHost = import.meta.env.VITE_SERVER_HOST;

import { _fetch } from "./fetch.js";
const FILE_API_URL = `${serverHost}/api/files`;

export const uploadFilesAction = (formData) => {
    return _fetch(FILE_API_URL, { method: 'POST', credentials: 'include', body: formData });
};
