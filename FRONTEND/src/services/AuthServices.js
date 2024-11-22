import { BASE_URL } from "../environment";

export const GetAccessToken = async ({ username, password }) => {
    try {
        const response = await fetch(`${BASE_URL}/autenticar/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                usuario: username,
                contrasena: password,
            }),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Obtener el token como texto (si es un JWT directo)
        const token = await response.text();
        console.log("JWT Token:", token);

        return token; // Retornar el JWT para su posterior uso
    } catch (error) {
        console.error("Error obteniendo el token de acceso:", error);
    }
};

