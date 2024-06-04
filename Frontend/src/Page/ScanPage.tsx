import { useEffect, useState } from "react";
import TokenManager from "../API/TokenManager";
import scanApi from "../API/ScannerApi";
import { useLocation } from "react-router-dom";
import NavBar from "../Component/Navbar";


function ScanPage(){
    const [claims, setClaims] = useState(TokenManager.getClaims());
    const [scan, setScan] = useState();
    const locationn = useLocation();

    const logout = () =>{
        location.href = "/";
        TokenManager.clear();
        setClaims(null);
    }

    const getScan = (scanId : any) => {
        scanApi.getScan(scanId)
        .then(data => {
            setScan(data);
            console.log("Scan data:",data);
        })
        .catch(error => {
            console.error(error);
        })
    }

    const renderVulnerabilities = () => {
        if (!scan || !scan.vulnerabilities) {
            return (
                <tr>
                    <td colSpan="4" className="text-center py-4">No vulnerabilities found</td>
                </tr>
            );
        }
        return scan.vulnerabilities.map((vuln, index) => (
            <tr key={index} className="bg-white odd:bg-gray-50 hover:bg-gray-100">
                <td className="px-6 py-4 border-r border-gray-200">{vuln.type}</td>
                <td className="px-6 py-4 border-r border-gray-200">{vuln.affectedUrl}</td>
                <td className="px-6 py-4 border-r border-gray-200">{vuln.description}</td>
                <td className="px-6 py-4">{vuln.mitigation}</td>
            </tr>
        ));
    }

    useEffect(() => {
        getScan(locationn.state.scanId);
        
    }, [locationn.state.scanId])

    return (
        <>
        <div>
            <NavBar logout={logout} />
        </div>
        <h2 className="text-5xl font-semibold mb-2 mt-32 text-center">Scan Results</h2>
            <div className="container mx-auto p-4 mt-6">
                {scan && scan.vulnerabilities && scan.vulnerabilities.length > 0 && (
                    <div className="mb-4">
                        <h3 className="text-2xl font-semibold mb-2">Risk Assessment Summary</h3>
                        <div className="overflow-x-auto">
                            <table className="min-w-full bg-white border border-gray-200 mb-4">
                                <thead>
                                    <tr>
                                        <th className="px-6 py-3 border-b-2 border-gray-200 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
                                        <th className="px-6 py-3 border-b-2 border-gray-200 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Risk Assessment</th>
                                        <th className="px-6 py-3 border-b-2 border-gray-200 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Risk Level</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {scan.vulnerabilities.map((vuln, index) => (
                                        <tr key={index} className="bg-white odd:bg-gray-50 hover:bg-gray-100">
                                            <td className="px-6 py-4 border-r border-gray-200">{vuln.type}</td>
                                            <td className="px-6 py-4 border-r border-gray-200">{vuln.riskAssessment}</td>
                                            <td className="px-6 py-4">{vuln.riskLevel}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                )}
                <div className="overflow-x-auto">
                    <h3 className="text-2xl font-semibold mb-2">Vulnerability Detection Scan</h3>
                    <table className="min-w-full bg-white border border-gray-200">
                        <thead>
                            <tr>
                                <th className="px-6 py-3 border-b-2 border-gray-200 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
                                <th className="px-6 py-3 border-b-2 border-gray-200 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Affected URL</th>
                                <th className="px-6 py-3 border-b-2 border-gray-200 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Description</th>
                                <th className="px-6 py-3 border-b-2 border-gray-200 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mitigation</th>
                            </tr>
                        </thead>
                        <tbody>
                            {renderVulnerabilities()}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    )

}

export default ScanPage;