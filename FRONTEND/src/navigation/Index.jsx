import { useContext } from "react";
import { AuthContext } from "../providers/AuthProvider";
import AuthNavigation from "./AuthNavigation";
import MainNavigation from "./MainNavigation";

const LoadingScreen = () => {
    return (
        <div className="flex justify-center items-center h-screen">
            <p>Loading...</p>
        </div>
    );
};

const ContainerNavigation = () => {

    const { token, isLoading } = useContext(AuthContext);

    if (isLoading) {
        return <LoadingScreen />;
    }

    return token ? <MainNavigation /> : <AuthNavigation />;

};

export default ContainerNavigation;
