import jwt_decode from "jwt-decode"

const TokenManager = {
    getAccessToken: () => sessionStorage.getItem("accessToken"),
    getClaims: () => {
        
        const claims = sessionStorage.getItem("claims");

        if (!claims){
            return undefined;
        }

        return JSON.parse(claims)
    },
    setAccessToken: (token : any) => {
        sessionStorage.setItem("accessToken", token);
        const claims = jwt_decode(token);
        sessionStorage.setItem("claims", JSON.stringify(claims))
        return claims;
    },
    clear: () =>{
        sessionStorage.removeItem("accessToken");
        sessionStorage.removeItem("claims");
    }
}

export default TokenManager;