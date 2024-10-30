import React from "react";
import { useAuth } from "../providers/AuthProvider"; // Asegúrate de que el path sea correcto
import AuthNavigation from "./AuthNavigation";
import MainNavigation from "./MainNavigation";

const LoadingScreen: React.FC = () => {
    
    return (
        <div className="flex justify-center items-center h-screen">
            <p>Loading...</p>
        </div>
    );
};

const ContainerNavigation: React.FC = () => {
    const { token, isLoading } = useAuth(); // Utiliza el hook personalizado para obtener el contexto

    if (isLoading) {
        return <LoadingScreen />;
    }

    return token ? <MainNavigation /> : <AuthNavigation />;
};

export default ContainerNavigation;
