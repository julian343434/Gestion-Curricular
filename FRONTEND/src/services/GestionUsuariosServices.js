
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
        const data = await response.json(); // Procesa el cuerpo de la respuesta
        return data; // Devuelve el objeto del usuario creado
    } catch (error) {
        console.error("Error creando el usuario:", error.message);
        throw error; // Re-lanzar el error para manejarlo en el nivel superior
    }
};


// Función para obtener usuarios
export const getUsers = async () => {
    try {
        // Obtener el token desde localStorage
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token no encontrado. Por favor, inicia sesión.");
        }

        // Construcción de la URL y los encabezados
        const url = `${BASE_URL}/usuario/`;
        const headers = {
            "Authorization": `Bearer ${token}`,
        };

        // Hacer la solicitud a la API
        const response = await fetch(url, {
            method: "GET",
            headers,
        });

        console.log("Respuesta del servidor:", response);

        // Manejo de errores en la respuesta HTTP
        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(`Error al obtener los usuarios: ${response.status} - ${errorMessage}`);
        }

        // Procesar y devolver la respuesta en formato JSON
        return await response.json();
    } catch (error) {
        console.error("Error obteniendo los usuarios:", error.message);
        throw error; // Re-lanzar el error para manejarlo en el nivel superior
    }
};

// services/GestionUsuariosServices.js

export async function eliminarUsuario(userId, token) {
     // Asegúrate de que BASE_URL esté definido correctamente
    const url = `${BASE_URL}/usuario/eliminar/${userId}`;
    const headers = {
      Authorization: `Bearer ${token}`
       // Opcional si necesitas enviar un cuerpo JSON, puedes omitirlo si no es necesario
    };
  
    try {
      const response = await fetch(url, {
        method: 'PATCH',
        headers
      });
  
      if (!response.ok) {
        throw new Error('Error al eliminar el usuario.');
      }
  
      return true; // Indica que el usuario fue eliminado correctamente
    } catch (error) {
      console.error('Error en eliminarUsuario:', error);
      throw error; // Lanza el error para que el componente lo maneje
    }
  }
  
// services/GestionUsuariosServices.js

export async function activarUsuario(userId, token) {
    // Asegúrate de que BASE_URL esté definido correctamente
   const url = `${BASE_URL}/usuario/activar/${userId}`;
   const headers = {
     Authorization: `Bearer ${token}`
      // Opcional si necesitas enviar un cuerpo JSON, puedes omitirlo si no es necesario
   };
 
   try {
     const response = await fetch(url, {
       method: 'PATCH',
       headers
     });
 
     if (!response.ok) {
       throw new Error('Error al activar el usuario.');
     }
 
     return true; // Indica que el usuario fue eliminado correctamente
   } catch (error) {
     console.error('Error en activarUsuario:', error);
     throw error; // Lanza el error para que el componente lo maneje
   }
 }
 
 // services/GestionUsuariosServices.js

export const actualizarRolUsuario = async (userId, { rol, periodo, anio }) => {
    try {
        // Validar que los campos obligatorios estén presentes
        if (!rol || !periodo || !anio) {
            throw new Error("Todos los campos son obligatorios para actualizar el rol del usuario.");
        }

        // Obtener el token desde localStorage
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token no encontrado. Por favor, inicia sesión.");
        }

        // Construcción de la URL y los encabezados
        const url = `${BASE_URL}/usuario/${userId}`;
        const headers = {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
        };

        // Cuerpo de la solicitud
        const body = JSON.stringify({ rol, periodo, anio });

        // Hacer la solicitud a la API
        const response = await fetch(url, {
            method: "PATCH",
            headers,
            body,
        });

        console.log("Respuesta del servidor:", response);

        // Manejo de errores en la respuesta HTTP
        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(`Error al actualizar el rol del usuario: ${response.status} - ${errorMessage}`);
        }

        // Procesar y devolver la respuesta en formato JSON
        return await response.json();
    } catch (error) {
        console.error("Error actualizando el rol del usuario:", error.message);
        throw error; // Re-lanzar el error para manejarlo en el nivel superior
    }
};
