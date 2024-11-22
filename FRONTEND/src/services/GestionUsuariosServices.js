import { BASE_URL } from "../environment";

// Función para crear un nuevo usuario
export const createUser = async ({ nombre, nombre_usuario, contrasena, correo, rol, periodo, anio }) => {
    try {
        // Validar que los campos obligatorios estén presentes
        if (!nombre || !nombre_usuario || !contrasena || !correo || !rol || !periodo || !anio) {
            throw new Error("Todos los campos son obligatorios para crear un usuario.");
        }

        // Obtener el token desde localStorage
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token no encontrado. Por favor, inicia sesión.");
        }

        // Construcción de la URL y los encabezados
        const url = `${BASE_URL}/usuario/guardar`;
        const headers = {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        };

        // Cuerpo de la solicitud
        const body = JSON.stringify({
            usuario: {
                nombre,
                nombre_usuario,
                contrasena,
                correo,
                activo: true,
            },
            rol,
            periodo,
            anio,
        });

        // Hacer la solicitud a la API
        const response = await fetch(url, {
            method: "POST",
            headers,
            body,
        });

        console.log("Respuesta del servidor:", response);

        // Manejo de errores en la respuesta HTTP
        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(`Error al crear el usuario: ${response.status} - ${errorMessage}`);
        }

        // Procesar y devolver la respuesta en formato JSON
        
        
    } catch (error) {
        console.error("Error creando el usuario:", error.message);
        throw error; // Re-lanzar el error para manejarlo en el nivel superior
    }
};
