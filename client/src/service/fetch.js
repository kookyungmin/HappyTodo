export const fetchGet = async (url) => {
    return _fetch(url, { method: 'GET', credentials: 'include' });
}

export const fetchPost = async (url, body = {}) => {
    return _fetch(url, { method: 'POST', headers: { 'content-Type': 'application/json' }, credentials: 'include', body: JSON.stringify(body) })
}

export const _fetch = async (url, requestInit) => {
    const res = await fetch(url, requestInit);
    let data = {}
    try {
        data = await res.json();
    } catch (error) {}
    return { isError: !res.ok, data };
}