import { useState } from "react";
import TokenManager from "../API/TokenManager";
import { ToastContainer, toast } from "react-toastify";


function ScanUrl(props : any){

    const [url, setUrl] = useState();
    const [claims, setClaims] = useState(TokenManager.getClaims());
    const [textAlert, setTextAlert] = useState('');

    const changedUrl = (e: any) => {
        setUrl(e.target.value);
    }

    const checkUserAuth = () => {
        if (!claims) {
            location.href = '/login';
        }
    }

    const handleMyServiceScan = (e: any) => {
        setTextAlert('');
        e.preventDefault();
        checkUserAuth();

        if (!url){
            setTextAlert("Please enter a url");
        }
        else{
            props.scan(url);
        }
    }

    const handleZapServiceScan = (e : any) => {
        setTextAlert('');
        e.preventDefault();
        checkUserAuth();

        if (!url){
            setTextAlert("Please enter a url");
        }
        else {
            props.zapScan(url);
        }
    }

    return (
        <>
            <div className="flex flex-col justify-center items-center h-screen">
                <label className="text-red-500">{textAlert}</label>
                <input
                    onChange={changedUrl}
                    type="text"
                    className="w-96 h-14 px-4 py-2 border rounded-xl focus:outline-none focus:ring focus:border-blue-300 mb-8"
                    placeholder="https:// or http://"
                    required
                />
                <div className="flex justify-between w-96">
                    <button
                    onClick={handleZapServiceScan}
                        type="button"
                        className="px-4 py-2 dark:bg-blue-800 text-white rounded-md hover:bg-blue-900 focus:outline-none focus:ring focus:border-blue-300"
                    >
                        ZAP Service
                    </button>
                    <button
                        onClick={handleMyServiceScan}
                        type="button"
                        className="px-4 py-2 dark:bg-red-700 text-white rounded-md hover:bg-red-800 focus:outline-none focus:ring focus:border-red-300"
                    >
                        Meda's Service
                    </button>
                </div>
                {props.scanned && (
                        <div className="mt-10 p-4 border rounded-md">
                            <p className="font-bold mb-5">Security-Zap-Report</p>
                            <a 
                                href={props.filePath} 
                                download="Security-Zap-Report.html" 
                                className="px-6 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 focus:outline-none focus:ring focus:border-green-300"
                            >
                                Download
                            </a>
                        </div>
                    )}
            </div>
        </>
    )
}
    
export default ScanUrl;