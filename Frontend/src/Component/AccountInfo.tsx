import { useEffect, useState } from "react";
import userApi from "../API/UserApi";
import { ToastContainer, toast } from "react-toastify";


function AccountInfo({userInfo}){
    const [username, setUsername] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const [textAlert, setTextAlert] = useState('');
    const [textError, setTextError] = useState('');
    const [usernameExist, setUsernameExist] = useState(false);
    const [emailExist, setEmailExist] = useState(false);

    useEffect(() => {

        if (userInfo && userInfo.username){
            setUsername(userInfo.username)
        }

        if (userInfo && userInfo.firstName){
            setFirstName(userInfo.firstName)
        }

        if (userInfo && userInfo.lastName){
            setLastName(userInfo.lastName)
        }

        if (userInfo && userInfo.email){
            setEmail(userInfo.email)
        }

    }, [userInfo])

    const usernameChanged = (e : any) => {
        setUsername(e.target.value);
    }

    const firstNameChanged = (e: any) => {
        setFirstName(e.target.value);
    }

    const lastNameChanged = (e: any) => {
        setLastName(e.target.value);
    }

    const emailChanged = (e: any) => {
        setEmail(e.target.value);
    }

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const isValidEmail = (email : string) => {
        const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        return emailRegex.test(email);
    };

    const checkUsernameExists = async (username : string) => {
        userApi.checkUsernameExists(username)
        .then(result => {
             setUsernameExist(result);
        })
        .catch(error => console.log(error))
    };

    const checkEmailExists = async (email : string) => {
        userApi.checkEmailExists(email)
        .then(result => {
            setEmailExist(result);
        })
        .catch(error => console.log(error))
    };

    const handleSaveSubmit = (e : any) => {
        setTextError('')
        setTextAlert('')

        if (username.length < 4){
            setTextError("Username must be at least 4 characters long")
        }
        else if(firstName.length < 4){
            setTextError("First Name must be at least 4 characters long")
        }
        else if (lastName.length < 4){
            setTextError("Last Name must be at least 4 characters long")
        }
        else if (!isValidEmail(email)){
            console.log("Email validation result:", isValidEmail(email));
            setTextError("Invalid email address")
        }
        else {
            checkUsernameExists(username);
            checkEmailExists(email);

            if (usernameExist){
                setTextError("Username Exists")
            }
            else if (emailExist){
                setTextError("Email Exists")
            }
            else {
                userApi.updateUser(userInfo.id, username, firstName, lastName, email)
                .then(result => {
                    console.log(result);
                    toast.success("Updated Successfully", {
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
                .catch(error => console.log(error))
            }
        }
    }

    return (
        <>
                <div className="max-w-xl mx-auto p-4 mt-52 mb-5 bg-gray-100 rounded shadow-md">
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">Username:</label>
                        <input
                        type="text"
                        className="appearance-none border rounded w-full py-2 px-3 pr-20 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="username"
                        value={username}
                        readOnly={!isEditing}
                        onChange={usernameChanged}
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">First Name:</label>
                        <input
                        type="text"
                        className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="firstName"
                        value={firstName}
                        readOnly={!isEditing}
                        onChange={firstNameChanged}
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">Last Name:</label>
                        <input
                        type="text"
                        className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="lastName"
                        value={lastName}
                        readOnly={!isEditing}
                        onChange={lastNameChanged}
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">Email:</label>
                        <input
                        type="email"
                        className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        name="email"
                        value={email}
                        readOnly={!isEditing}
                        onChange={emailChanged}
                        />
                    </div>
                    <div className="flex justify-between">
                        {!isEditing ? (
                        <button
                            className="bg-purple-500 dark:hover:bg-purple-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                            onClick={handleEditClick}
                        >
                            Edit
                        </button>
                        ) : (
                        <button
                        type="button"
                            className="bg-blue-500 dark:hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                            onClick={handleSaveSubmit}
                        >
                            Save
                        </button>
                        )}
                        <p className="font-semibold text-green-500">{textAlert}</p>
                        <p className="font-semibold text-red-800">{textError}</p>
                    </div>
                    </div>
                    <ToastContainer />
        </>
    )
}

export default AccountInfo;