import { useState } from "react";
import { Link } from "react-router-dom";


function LoginForm(props : any){

    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    const usernameChanged = (e : any) => {
        setUsername(e.target.value);
    }

    const passwordChanged = (e : any) => {
        setPassword(e.target.value);
    }

    const handleSubmit = (e : any) => {
        e.preventDefault();
        try{
            props.login(username, password);
        }
        catch{
            console.log("Either Username or password is wrong");
        }
    }

    return (
        <>
            <section className="gradient-form h-full flex items-center justify-center">
                <div className="container h-full p-10">
                    <div className="flex h-full flex-wrap items-center justify-center text-neutral-800 dark:text-neutral-200">
                        <div className="w-full">
                            <div className="block rounded-lg bg-white shadow-lg dark:bg-neutral-800">
                                        <div className="md:mx-6 md:p-12">
                                            <div className="text-center">
                                                <h4 className="mb-12 mt-1 pb-1 text-3xl font-semibold">
                                                    Welcome Back
                                                </h4>
                                            </div>
                                            <form onSubmit={handleSubmit}>
                                                <p className="mb-4 font-semibold text-xl">Please login to your account</p>
                                                <div className="relative mb-6" data-twe-input-wrapper-init>
                                                    <input
                                                        onChange={usernameChanged}
                                                        type="text"
                                                        className="peer block min-h-[auto] w-full rounded border border-gray-300 bg-transparent px-3 py-[0.32rem] leading-[1.6] outline-none transition-all duration-200 ease-linear focus:border-primary focus:placeholder:opacity-100 peer-focus:text-primary data-[twe-input-state-active]:placeholder:opacity-100 motion-reduce:transition-none dark:border-neutral-600 dark:text-white dark:placeholder:text-neutral-300 dark:autofill:shadow-autofill dark:peer-focus:border-primary dark:peer-focus:text-primary [&:not([data-twe-input-placeholder-active])]:placeholder:opacity-0"
                                                        placeholder="Username"
                                                    />
                                                    <label
                                                        className="pointer-events-none absolute left-3 top-0 mb-0 max-w-[90%] origin-[0_0] truncate pt-[0.37rem] leading-[1.6] text-neutral-500 transition-all duration-200 ease-out peer-focus:-translate-y-[0.9rem] peer-focus:scale-[0.8] peer-focus:text-primary peer-data-[twe-input-state-active]:-translate-y-[0.9rem] peer-data-[twe-input-state-active]:scale-[0.8] motion-reduce:transition-none dark:text-neutral-400 dark:peer-focus:text-primary"
                                                    >
                                                        Username
                                                    </label>
                                                </div>
                                                <div className="relative mb-6" data-twe-input-wrapper-init>
                                                <input
                                                    onChange={passwordChanged}
                                                    type="password"
                                                    className="peer block min-h-[auto] w-full rounded border border-gray-300 bg-transparent px-3 py-[0.32rem] leading-[1.6] outline-none transition-all duration-200 ease-linear focus:border-primary focus:placeholder:opacity-100 peer-focus:text-primary data-[twe-input-state-active]:placeholder:opacity-100 motion-reduce:transition-none dark:border-neutral-600 dark:text-white dark:placeholder:text-neutral-300 dark:autofill:shadow-autofill dark:peer-focus:border-primary dark:peer-focus:text-primary [&:not([data-twe-input-placeholder-active])]:placeholder:opacity-0"
                                                    placeholder="Password"
                                                />
                                                    <label
                                                        className="pointer-events-none absolute left-3 top-0 mb-0 max-w-[90%] origin-[0_0] truncate pt-[0.37rem] leading-[1.6] text-neutral-500 transition-all duration-200 ease-out peer-focus:-translate-y-[0.9rem] peer-focus:scale-[0.8] peer-focus:text-primary peer-data-[twe-input-state-active]:-translate-y-[0.9rem] peer-data-[twe-input-state-active]:scale-[0.8] motion-reduce:transition-none dark:text-neutral-400 dark:peer-focus:text-primary"
                                                    >
                                                        Password
                                                    </label>
                                                </div>
                                                <div className="mb-12 pb-1 pt-1 text-center">
                                                    <button
                                                        className="mb-3 inline-block w-full rounded px-6 pb-2 pt-2.5 font-medium uppercase leading-normal text-white shadow-dark-3 transition duration-150 ease-in-out hover:shadow-dark-2 focus:shadow-dark-2 focus:outline-none focus:ring-0 active:shadow-dark-2 dark:shadow-black/30 dark:hover:shadow-dark-strong dark:focus:shadow-dark-strong dark:active:shadow-dark-strong"
                                                        type="submit"
                                                        data-twe-ripple-init
                                                        data-twe-ripple-color="light"
                                                        style={{ background: "#00a2a2" }}
                                                    >
                                                        Log in
                                                    </button>
                                                    <a href="#!">Forgot password?</a>
                                                </div>
                                                <div className="flex items-center justify-between pb-6">
                                                    <p className="mb-0 me-2">Don't have an account?</p>
                                                    <Link
                                                        to={"/Register"}
                                                        type="button"
                                                        className="inline-block rounded border-2 border-danger px-6 pb-[6px] pt-2 text-xs font-medium uppercase leading-normal text-danger transition duration-150 ease-in-out hover:border-danger-600 hover:bg-danger-50/50 hover:text-danger-600 focus:border-danger-600 focus:bg-danger-50/50 focus:text-danger-600 focus:outline-none focus:ring-0 active:border-danger-700 active:text-danger-700 dark:hover:bg-cyan-950 dark:focus:bg-cyan-950"
                                                        data-twe-ripple-init
                                                        data-twe-ripple-color="light"
                                                    >
                                                        Register
                                                    </Link>
                                                </div>
                                            </form>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
        </>
    )
}

export default LoginForm;