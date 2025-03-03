const serverHost = import.meta.env.VITE_SERVER_HOST;

import {_fetch, fetchGet} from "./fetch.js";
export const FILE_API_URL = `${serverHost}/api/files`;

export const uploadFilesAction = (formData) => {
    return _fetch(FILE_API_URL, { method: 'POST', credentials: 'include', body: formData });
};

export const getImageAction = (uniqueFileName) => {
    return fetchGet(`${FILE_API_URL}/image/${uniqueFileName}`, true);
}
