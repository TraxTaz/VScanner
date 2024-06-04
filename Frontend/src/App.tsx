import './App.css'
import { NavLink, Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import HomePage from './Page/HomePage'
import LoginPage from './Page/LoginPage'
import SignUpPage from './Page/SignUpPage'
import AccountPage from './Page/AccountPage'
import 'react-toastify/ReactToastify.css'
import ScanPage from './Page/ScanPage'
import ScanHistory from './Page/ScanHistory'

function App() {

  const link = {
    path:"/",
  }

  return (
    <>
        <Router>
          <NavLink to={link.path} />
          <Routes>
            <Route path='/' element={<HomePage />} />
            <Route path='/Login' element={<LoginPage />} />
            <Route path='/Register' element={<SignUpPage />} />
            <Route path='/AccountInfo' element={<AccountPage />} />
            <Route path='/scan' element={<ScanPage />} />
            <Route path='/Scan-History' element={<ScanHistory />} />
          </Routes>
        </Router>
    </>
  )
}

export default App
