import {NextUIProvider} from "@nextui-org/react";
import {AuthProvider} from './providers/AuthProvider';
import ContainerNavigation from "./navigation/Index";
import MainLayout from "./layouts/MainLayout";

function App() {
    return (
        <NextUIProvider>
            <AuthProvider>
                <MainLayout>
                    <ContainerNavigation/>
                </MainLayout>
            </AuthProvider>
        </NextUIProvider>
    );
}

export default App;
