import axios from 'axios'
import { LoginResponse } from '../Types/LoginResponse'
import TokenManager from './TokenManager'
import apiURL from './ApiURL'

class LoginApi{
    async login(username: string, password: string): Promise<LoginResponse> {
        const response = await axios.post<LoginResponse>(apiURL+"/tokens", {username, password});

        const accessToken = response.data.token;
        TokenManager.setAccessToken(accessToken);

        return response.data;
    }
}

export default new LoginApi();