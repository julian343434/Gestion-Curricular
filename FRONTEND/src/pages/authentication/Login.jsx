import React, { useState, useContext } from "react";
import { Button } from "@nextui-org/button";
import { Input } from "@nextui-org/input";
import { Link, useNavigate } from "react-router-dom";
import { Spinner } from "@nextui-org/spinner";
import { GetAccessToken } from "../../services/AuthServices"; // Asegúrate de que esta función maneje los roles.
import { AuthContext } from "../../providers/AuthProvider";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const { setToken, setUserRole, setSession } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
    
        try {
            // Llama a la función GetAccessToken con el username y password
            const { token, payload } = await GetAccessToken({ username, password });
    
            if (token) {
                // Guarda el token en localStorage y en el contexto
                localStorage.setItem("token", token);
    
                // Obtiene el rol del usuario desde la payload del JWT
                const userRole = payload?.rol || "Estudiante"; // Por defecto, Estudiante
    
                // Guarda el rol, sesión y token en el almacenamiento local y contexto
                localStorage.setItem("role", userRole);
                localStorage.setItem("session", true);
                setToken(token);
                setUserRole(userRole);
                setSession(true);
    
                // Redirige según el rol del usuario
                switch (userRole) {
                    case "Administrador":
                        navigate("/user-admin");
                        break;
                    case "Profesor de Comite":
                        navigate("/comite");
                        break;
                    case "Profesor":
                        navigate("/profesor");
                        break;
                    case "Estudiante":
                    default:
                        navigate("/home");
                        break;
                }
            } else {
                // Si no se obtiene un JWT válido
                alert("Credenciales incorrectas");
            }
        } catch (error) {
            console.error("Error durante la autenticación:", error);
            alert("Hubo un error al intentar iniciar sesión.");
        } finally {
            setIsLoading(false);
        }
    };
    

    return (

        <div className="flex items-center justify-center bg-white w-full h-full">
            <div className="bg-red-100 p-8 rounded-lg shadow-lg max-w-md w-full">
                <div className="flex justify-center mb-6">
                    <img src="./logo.png" className="w-24 h-24" />
                </div>
                <h2 className="text-2xl font-semibold text-center text-red-700 mb-6">Iniciar Sesión</h2>
                <form className="space-y-4" onSubmit={handleSubmit}>
                    <Input
                        color="secondary"
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="Nombre de usuario"
                        required
                        fullWidth
                        size="lg"
                        className="bg-red-100 text-red-200"
                    />
                    <Input
                        color="secondary"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Contraseña"
                        required
                        fullWidth
                        size="lg"
                        className="bg-red-50 text-red-200"
                    />
                    <Button
                        type="submit"
                        color="secondary"
                        size="lg"
                        className="w-full bg-red-400 hover:bg-red-600 text-white"
                    >
                        {isLoading ? <Spinner color="white" /> : "Ingresar"}
                    </Button>
                </form>
            </div>
        </div>
    );
        
}
