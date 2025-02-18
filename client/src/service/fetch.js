export const fetchGet = async (url) => {
    return _fetch(url, { method: 'GET', credentials: 'include' });
}

export const fetchPost = async (url, body = {}) => {
    return _fetch(url, { method: 'POST', headers: { 'content-Type': 'application/json' }, credentials: 'include', body: JSON.stringify(body) })
}

export const fetchDelete = async (url) => {
    return _fetch(url, { method: 'DELETE', credentials: 'include' });
}

export const fetchPut = async (url, body) => {
    return _fetch(url, { method: 'PUT', credentials: 'include', headers: { 'content-Type': 'application/json' }, body: JSON.stringify(body)});
}

export const _fetch = async (url, requestInit) => {
    if (sessionStorage.getItem("atk")) {
        requestInit.headers = { ...requestInit.headers, 'Authorization': sessionStorage.getItem("atk")}
    }
    const res = await fetch(url, requestInit);
    let data = {}
    try {
        if (res.headers.get('atk')) {
            sessionStorage.setItem("atk", res.headers.get('atk'));
        }
        data = await res.json();
    } catch (error) {}
    return { isError: !res.ok, data };
}