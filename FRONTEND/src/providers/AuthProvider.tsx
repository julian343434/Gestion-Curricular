import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';

interface AuthContextType {
    token: string | null;
    isLoading: boolean;
    setToken: (token: string | null) => void; // Agregar la función para establecer el token
    setUserRole: (role: string) => void; // Agregar la función para establecer el rol del usuario
}

// Define las propiedades del AuthProvider
interface AuthProviderProps {
    children: ReactNode; // Esto define children como un nodo React
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [isLoading, setIsLoading] = useState(true);
    const [token, setToken] = useState<string | null>(null);
    const [userRole, setUserRole] = useState<string | null>(null); // Estado para el rol del usuario

    useEffect(() => {
        // Simulación de una llamada a una API para verificar el token
        const fetchToken = async () => {
            // Aquí deberías incluir tu lógica para obtener el token
            const retrievedToken = await new Promise<string | null>((resolve) => {
                setTimeout(() => resolve("your-token-here"), 1000); // Simulación de espera
            });
            setToken(retrievedToken);
            setIsLoading(false);
        };

        fetchToken();
    }, []);

    return (
        <AuthContext.Provider value={{ token, isLoading, setToken, setUserRole }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};

// Exporta el AuthContext para que pueda ser utilizado en otros componentes
export { AuthContext };
