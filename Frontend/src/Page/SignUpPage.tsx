
import { useState } from "react";
import TokenManager from "../API/TokenManager";
import userApi from "../API/UserApi";
import NavBar from "../Component/Navbar";
import SignUpForm from "../Component/SignUpForm";
import { ToastContainer, toast } from "react-toastify";


function SignUpPage(){

    const [claims, setClaims] = useState(TokenManager.getClaims())

    const logout = () =>{
        location.href = "/";
        TokenManager.clear();
        setClaims(null);
    }


    const createUser = (username : string, password : string, firstName : string, lastName : string, email : string) => {
        userApi.post(username, password, firstName, lastName, email)
        .then(result => {
            toast.success("Account Created Successfully", {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "dark",
            });
        })
        .catch(error => {
            toast.error("Username Exists", {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "dark",
            });
            console.log(error);
        })
    }

    return (
        <>
            <div>
                <NavBar logout={logout} />
            </div>
            <div className="mt-40">
                <SignUpForm register={createUser} />
            </div>
            <ToastContainer />
        </>
    )
}

export default SignUpPage;