import { useEffect, useState } from "react";
import TokenManager from "../API/TokenManager";
import NavBar from "../Component/Navbar";
import userApi from "../API/UserApi";
import AccountInfo from "../Component/AccountInfo";


function AccountPage(){
    const [claims, setClaims] = useState(TokenManager.getClaims())
    const [userInfo, setUserInfo] = useState();

    const logout = () =>{
        location.href = "/";
        TokenManager.clear();
        setClaims(null);
    }

    const getUserInfo = () => {
        if (claims){
            userApi.get(claims.userID)
            .then(data => {
                setUserInfo(data.data);
            })
            .catch(error => console.log(error))
        }
        else {
            location.href = "/Login"
        }
    }

    useEffect(() => {
        getUserInfo();
    }, [])

    return (
        <>
            <div>
                <NavBar logout={logout} />
            </div>
            <div>
                <AccountInfo userInfo={userInfo} />
            </div>
        </>
    )
}

export default AccountPage;