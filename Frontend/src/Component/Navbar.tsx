import { useState } from "react";
import { Link } from "react-router-dom";
import TokenManager from "../API/TokenManager";

function NavBar(props : any){

    const [claims, setClaims] = useState(TokenManager.getClaims());
    const [isOpen, setIsOpen] = useState(false);

    const handleAccount = () => {
        if (!claims) {
          location.href = '/Login';
        } else {
          location.href = '/Account';
        }
    };

    return (
        <>
            <nav className="bg-white dark:bg-neutral-800 fixed w-full z-20 top-0 start-0 border-b border-neutral-200 dark:border-neutral-600">
                <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                <a href="/" className="flex items-center space-x-3 rtl:space-x-reverse">
                    <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">VScanner</span>
                </a>
                <div className="flex md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">

                {claims == null || claims == undefined ? (
                              <Link to={"/Login"}
                              type="button"
                              className="text-white bg-cyan-600 hover:bg-cyan-800 focus:ring-4 focus:outline-none focus:ring-cyan-300 font-medium rounded-lg text-sm px-4 py-2 text-center dark:hover:bg-cyan-900 dark:focus:ring-cyan-800">
                              Login
                          </Link>
                        ) : (
                            <button onClick={() => setIsOpen((prev) => !prev)} className="relative text-white">
                            <div className="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-neutral-600">
                                <svg className="absolute w-12 h-12 text-neutral-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                    <path fillRule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clipRule="evenodd"></path>
                                </svg>
                            </div>

                        {isOpen && (
                            <div className="absolute right-0 mt-2 w-32 bg-white dark:bg-neutral-900 rounded-lg shadow-md">
                                <ul className="py-2">
                                    <li><Link to="/AccountInfo" className="block px-4 py-2 text-neutral-900 dark:text-white hover:bg-gray-100 dark:hover:bg-neutral-600">Account</Link></li>
                                    <li><Link to="/Scan-History" className="block px-4 py-2 text-neutral-900 dark:text-white hover:bg-gray-100 dark:hover:bg-neutral-600">Scan History</Link></li>
                                    <li><Link to={"#"} onClick={props.logout} className="block w-full px-4 py-2 text-center text-neutral-600 dark:text-white hover:bg-gray-100 dark:hover:bg-neutral-600">Logout</Link></li>
                                </ul>
                            </div>
                        )}
                    </button>
                        )}
                </div>
                <div className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-sticky">
                    <ul className="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
                    </ul>
                </div>
                </div>
                </nav>
        </>
    )
}

export default NavBar;