import Login from "../pages/authentication/Login";
import Register from "../pages/authentication/RegisterPage";
import DeniedPage from "../pages/other/Denied";
import { BrowserRouter, Route, Routes } from "react-router-dom";


export default function AuthNavigation() {
    
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="*" element={<DeniedPage />} />
            </Routes>
        </BrowserRouter>
    );
}