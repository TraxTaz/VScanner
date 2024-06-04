import { useState } from "react";
import LoginForm from "../Component/LoginForm";
import NavBar from "../Component/Navbar";
import TokenManager from "../API/TokenManager";
import LoginApi from "../API/LoginApi";
import { ToastContainer, toast } from "react-toastify";


function LoginPage(){

    const [claims, setClaims] = useState(TokenManager.getClaims());

    const handleLogin = async (username : string, password : string) => {
        try{
            const claims = await LoginApi.login(username, password);
            setClaims(claims);
        }
        catch{
            toast.error("Either username or password is incorrect", {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "dark",
            });
        }
    }

    if (claims != null){
        location.href = "/";
    }

    return (
        <>
        <div>
            <NavBar />
        </div>
        <div className="mt-40">
            <LoginForm login={handleLogin} />
        </div>
        <ToastContainer />
        </>
    )
}

export default LoginPage;