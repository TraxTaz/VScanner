import { useEffect, useState } from "react";
import NavBar from "../Component/Navbar";
import TokenManager from "../API/TokenManager";
import ScanUrl from "../Component/Url";
import scanApi from "../API/ScannerApi";
import { ToastContainer, toast } from "react-toastify";
import zapScanApi from "../API/ZapScannerApi";
import { useNavigate } from "react-router-dom";


function HomePage(){

    const [claims, setClaims] = useState(TokenManager.getClaims())
    const [filePath, setFilePath] = useState('');
    const [scanned, setScanned] = useState(false);
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);

    const logout = () =>{
        location.href = "/";
        TokenManager.clear();
        setClaims(null);
    }

    const scan = (url : String) => {
        setIsLoading(true); // Set loading to true when starting scan
        scanApi.post(url, claims.userID)
        .then(result => {
            console.log(result);
            toast.success("Scan Completed", {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "dark",
            });
            navigate('/scan', {state: {scanId: result.id}});
        })
        .catch(error => {
            console.log(error)
            toast.error("Failed to scan, try again or try a different url", {
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
        .finally(() => {
            setIsLoading(false);
        });
    }

    const zapScan = (url : string) => {
        setIsLoading(true); // Set loading to true when starting zap scan
        setScanned(false);
        zapScanApi.scan(url)
        .then(response => {
            const url = window.URL.createObjectURL(new Blob([response]));
            setFilePath(url);
            setScanned(true);
            toast.success("Zap finished scanning", {
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
            console.error('Error downloading the report:', error);
            toast.error("Failed to download the report", {
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
        .finally(() => {
            setIsLoading(false);
        });
    };


    return (
        <>
            <div>
                <NavBar logout={logout} />
            </div>
            <div>
                {isLoading ? ( // Show loading indicator when isLoading is true
                    <div className="mt-32 text-center text-3xl font-semibold">Loading... (max 2 mins)</div>
                ) : (
                    <ScanUrl scan={scan} zapScan={zapScan} filePath={filePath} scanned={scanned} />
                )}
            </div>
            <ToastContainer />
        </>
    )
}

export default HomePage;