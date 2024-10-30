import React, { useState, useContext } from "react";
import { Button } from "@nextui-org/button";
import { Input } from "@nextui-org/input";
import { Link, useNavigate } from "react-router-dom";
import { Spinner } from "@nextui-org/spinner";
import { AuthContext } from "../../providers/AuthProvider"; // Asegúrate de que esta importación sea correcta

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const { setToken, setUserRole } = useContext(AuthContext) || {}; // Maneja el caso donde AuthContext es undefined
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);

        // Comprobación de credenciales del administrador
        if (username === "admin" && password === "admin") {
            const adminJwt = "fake-jwt-for-admin"; // Aquí puedes crear un JWT ficticio si no estás usando uno real
            localStorage.setItem('token', adminJwt);
            localStorage.setItem('role', "admin");
            setToken(adminJwt);
            setUserRole("admin");
            navigate("/user-admin");
            setIsLoading(false);
            return;
        }

        // Si no es admin, permite que inicie sesión con cualquier contraseña
        const userJwt = "fake-jwt-for-user"; // JWT ficticio para usuarios normales
        localStorage.setItem('token', userJwt);
        localStorage.setItem('role', "user"); // Persistencia del rol para usuarios normales
        setToken(userJwt);
        setUserRole("user");
        navigate("/home"); // Redirige a la ruta de usuarios normales
        setIsLoading(false);
    };

    return (
        <div className="flex items-center justify-center bg-rose-100 w-full h-full">
            <div className="bg-white p-8 rounded-lg shadow-lg max-w-md w-full">
                <h1 className="text-3xl font-bold text-center text-rose-600 mb-6">Mueve Villavo</h1>
                <h2 className="text-2xl font-semibold text-center text-pink-700 mb-6">Iniciar Sesión</h2>
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
                        className="bg-pink-100 text-pink-200"
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
                        className="bg-pink-50 text-pink-200"
                    />
                    <Button
                        type="submit"
                        color="secondary"
                        size="lg"
                        className="w-full bg-rose-400 hover:bg-rose-600 text-white"
                    >
                        {isLoading ? <Spinner color="white" /> : "Ingresar"}
                    </Button>
                </form>
                <div className="mt-6 text-center">
                    <Link
                        to="/register"
                        className="text-rose-600 hover:text-rose-800 transition-colors duration-300"
                    >
                        ¿No tienes cuenta? Regístrate
                    </Link>
                </div>
            </div>
        </div>
    );
}
