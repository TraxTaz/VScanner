import { useEffect, useState } from "react";
import TokenManager from "../API/TokenManager";
import NavBar from "../Component/Navbar";
import scanApi from "../API/ScannerApi";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";


function ScanHistory(){

    const [claims, setClaims] = useState(TokenManager.getClaims())
    const [scans, setScans] = useState<any[]>([]);
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = useState(1);
    const [scansPerPage] = useState(6);

    const logout = () =>{
        location.href = "/";
        TokenManager.clear();
        setClaims(null);
    }

    const getScansForUser = () => {
        scanApi.getScansForUser(claims.userID)
        .then(data => {
            setScans(data);
            console.log(data);
        })
        .catch(error => {
            console.log(error);
            toast.error("Something went wrong, please try again", {
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
    }

    const handleView = (scanId : any) => {
        navigate('/scan', {state: {scanId: scanId}});
    }

    const indexOfLastScan = currentPage * scansPerPage;
    const indexOfFirstScan = indexOfLastScan - scansPerPage;
    const currentScans = scans.slice(indexOfFirstScan, indexOfLastScan);

    const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

    useEffect(() => {
        getScansForUser();
    }, [])

    return (
        <>
        <div>
                <NavBar logout={logout} />
            </div>
            <div className="mt-32 mx-auto max-w-4xl">
                <h1 className="text-center text-4xl font-semibold mb-8">Scan History</h1>
                {scans.length === 0 ? (
                    <div className="text-xl text-center text-gray-600">No scans available</div>
                ) : (
                    <>
                        {currentScans.map((scan, index) => (
                            <div key={index} className="border border-gray-300 rounded-lg overflow-hidden shadow-md mb-6 transform hover:scale-105 transition duration-300">
                                <div className="bg-gradient-to-r from-neutral-500 to-purple-500 px-6 py-4">
                                    <h2 className="text-xl font-bold text-white">{index + 1}: {scan.url}</h2>
                                </div>
                                <div className="p-6">
                                    {scan.vulnerabilities.map((vuln, vulnIndex) => (
                                        <div key={vulnIndex} className="mb-4">
                                            <p className="font-semibold text-lg text-gray-800">{vuln.type}</p>
                                            <p className="text-gray-600">{vuln.riskAssessment}</p>
                                        </div>
                                    ))}
                                    <button onClick={() => handleView(scan.id)} className="bg-gradient-to-r from-neutral-500 to-purple-500 text-white font-semibold py-2 px-4 rounded mt-4">View</button>
                                </div>
                            </div>
                        ))}
                        {/* Pagination */}
                        <div className="flex justify-center mt-4">
                            <nav>
                                <ul className="pagination flex gap-4">
                                    {Array.from({ length: Math.ceil(scans.length / scansPerPage) }, (_, i) => (
                                        <li key={i} className="page-item">
                                            <button onClick={() => paginate(i + 1)} className="page-link bg-gray-300 hover:bg-gray-400 text-gray-800 font-semibold py-2 px-4 rounded">{i + 1}</button>
                                        </li>
                                    ))}
                                </ul>
                            </nav>
                        </div>
                    </>
                )}
            </div>
            <ToastContainer />
        </>
    )
}

export default ScanHistory;