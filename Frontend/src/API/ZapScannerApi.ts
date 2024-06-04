import axios from "axios"
import apiURL from "./ApiURL"
import TokenManager from "./TokenManager"


const zapScanApi = {
    scan: (url : string) => {
        return axios.post(apiURL + `/scan`, url, {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}`}
        })
        .then(response => response.data)
    }
}

export default zapScanApi;