import { BASE_URL } from "../environment";

export const uploadExcelFile = async (file, nombre, descripcion, anio, token) => {
  const url = `${BASE_URL}/planEstudio/guardarArchivo`;

  // Crear el FormData
  const formData = new FormData();
  formData.append("file", file);
  formData.append("nombre", nombre);
  formData.append("descripcion", descripcion);
  formData.append("anio", anio);

  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`, // Agregar el token JWT
      },
      body: formData, // Pasar el FormData
    });

    if (!response.ok) {
      throw new Error(`Error al subir el archivo: ${response.statusText}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error al subir el archivo:", error);
    throw error;
  }
};


export const fetchPlanEstudio = async (token) => {
  const url = `${BASE_URL}/planEstudio/planEstudio`;

  try {
    const response = await fetch(url, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`, // Agregar el token JWT
        "Content-Type": "application/json", // Definir el tipo de contenido
      },
    });

    if (!response.ok) {
      throw new Error(`Error al obtener el plan de estudio: ${response.statusText}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error al obtener el plan de estudio:", error);
    throw error;
  }
};