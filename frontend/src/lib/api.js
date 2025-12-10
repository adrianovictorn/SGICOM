// @ts-nocheck
import { auth } from "./auth";


const BASE = "";


function withAuth(headers = {}){
    const t = auth.getToken();
    return t ? {...headers, Authorization: `Bearer ${t}`} : headers;
}

export async function getApi(path) {
    const res = await fetch(BASE + path, {headers: withAuth()});
    if(!res.ok) throw new Error(`GET ${path} -> ${res.status}`);
    try{ return await res.json();} catch{return null;}
    
}


export async function postApi(path, body) {
    const res = await fetch(BASE + path, {
        method: 'POST',
        headers: withAuth({ 'Content-Type': 'application/json'}),
        body: JSON.stringify(body ?? {})
    });

    if(!res.ok) throw new Error(`POST ${path} -> ${res.status}`)
        return await res.json();
    
}

export async function putApi(path, body) {
    const res = await fetch(BASE + path, {
        method: 'PUT',
        headers: withAuth({ 'Content-Type': 'application/json'}),
        body: JSON.stringify(body ?? {})
    })    
}

export async function patchApi(path){
    const res = await fetch(BASE + path, {
        method: 'PATCH',
        headers: withAuth()
    })

    if(!res.ok) throw new Error(`PATCH METHOD ${path} -> ${res.status}`)
    try{
        return await res.json();
    }catch{
        return null;
    }
}


export async function deleteApi(path) {
    const res = await fetch(BASE + path, {
        method: 'DELETE',
        headers: withAuth()
    })

    if(!res.ok) throw new Error(`DELETE METHOD ${path} -> ${res.status}`);
    try{
        return await res.json();
    }
    catch{
        return null;
    }
    
}