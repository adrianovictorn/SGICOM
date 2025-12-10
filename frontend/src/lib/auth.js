// @ts-nocheck

const KEY = 'mm_access_token'

let token = typeof window !== 'undefined' ? sessionStorage.getItem(KEY) : null;

function parseJwt(token){
    if(!token) return null;

    const parts = token.split('.');
    if(parts.length !== 3) return null;

    try{
        const base64Url = parts[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
            atob(base64)
            .split('')
            .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
            .join('')
        );

        return JSON.parse(jsonPayload);
    }catch(e){
        console.error('Erro ao decodificar JWT', e)
        return null;
    }
}

export const auth = {
    setToken(t) {
        token = t;
        if (typeof window !== 'undefined'){
            t ? sessionStorage.setItem(KEY, t) : sessionStorage.removeItem(KEY);
        }
    },


    getToken(){
        return token;
    },


    isAuth(){
        return !!token;
    },

    logout(){
        this.setToken(null)
    },

    getPayload(){
        return parseJwt(token);
    }
}