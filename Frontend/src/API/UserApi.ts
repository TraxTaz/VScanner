import axios from "axios";
import apiURL from "./ApiURL";
import TokenManager from "./TokenManager";


const userApi = {
    post: (username : string, password : string, firstName : string, lastName : string, email : string) => {
        const requestBody = {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            email:email
        };

        return axios.post(apiURL + "/users/create", requestBody);
    },
    get: (userID : any) => {
        return axios.get(apiURL + `/users/${userID}`, {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}`}
        })
    },
    checkUsernameExists: (username : any) => {
        return axios.get(apiURL + `/users/check/username/${username}`)
        .then(response => response.data)
    },
    checkEmailExists: (email : any) => {
        return axios.get(apiURL + `/users/check/email/${email}`)
        .then(response => response.data)
    },
    updateUser: (id : any, username : string, firstName : string, lastName : string, email : string) => {
        const requestBody = {
            id: id,
            username: username,
            firstName: firstName,
            lastName: lastName,
            email:email
        };

        return axios.put(apiURL + `/users/update/${id}`, requestBody, {
            headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}`}
        })
        .then(response => response.data)
    }
}

export default userApi;