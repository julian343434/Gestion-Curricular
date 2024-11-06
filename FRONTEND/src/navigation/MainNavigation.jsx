import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "../pages/home/Home";
import UserAdmin from "../pages/home/UserAdmin";
import PageNotFound from "../pages/other/PageNotFound";
import AlertasFormulario from "../components/AlertasFormulario";

export default function MainNavigation() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/home" element={<Home />} />
                <Route path="/user-admin" element={<UserAdmin />} />
                <Route path="/user-admin/crear-alertas" element={<AlertasFormulario />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </BrowserRouter>
    );
}