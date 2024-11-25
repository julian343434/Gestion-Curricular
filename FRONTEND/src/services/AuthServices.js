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

        // Obtener el token como texto (JWT)
        const token = await response.text();
        console.log("JWT Token:", token);

        // Decodificar la carga útil (payload)
        const payload = decodeJwtPayload(token);
        console.log("Payload:", payload);

        return { token, payload }; // Retornar el JWT y la carga útil
    } catch (error) {
        console.error("Error obteniendo el token de acceso:", error);
    }
};

// Función para decodificar la carga útil (payload) de un token JWT
const decodeJwtPayload = (token) => {
    try {
        // Dividir el token en sus partes (header, payload, signature)
        const parts = token.split(".");
        if (parts.length !== 3) {
            throw new Error("Invalid JWT format");
        }

        // Decodificar la carga útil (segunda parte del token)
        const base64Url = parts[1];
        const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
        const jsonPayload = decodeURIComponent(
            atob(base64)
                .split("")
                .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
                .join("")
        );

        return JSON.parse(jsonPayload); // Convertir a objeto JavaScript
    } catch (error) {
        console.error("Error decodificando el JWT:", error);
        return null;
    }
};
