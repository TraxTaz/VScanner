import axios from "axios";
import apiURL from "./ApiURL";
import TokenManager from "./TokenManager";


const scanApi = {
    post: (url : any, userID : any) => {
        console.log(url);
        const encodedUrl = encodeURIComponent(url);
        return axios.post(apiURL + `/scan/${userID}`, encodedUrl, {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}`}
        })
        .then(response => response.data)
    },
    getScan: (scanId : any) => {
        return axios.get(apiURL + `/scan/${scanId}`, {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}`}
        })
        .then(response => response.data)
    },
    getScansForUser: (userId : any) => {
        return axios.get(apiURL + `/scan/getScans/${userId}`, {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}`}
        })
        .then(response => response.data)
    }
}

export default scanApi;