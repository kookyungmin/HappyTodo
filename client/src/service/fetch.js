export const fetchGet = async (url) => {
    return _fetch(url, { method: 'GET', credentials: 'include' });

}

export const _fetch = async (url, requestInit) => {
    const res = await fetch(url, requestInit);
    const data = await res.json();
    return { isError: !res.ok, data };
}