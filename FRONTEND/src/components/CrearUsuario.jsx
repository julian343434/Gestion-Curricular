import React, { useState, useContext } from "react";
import { createUser } from "../services/GestionUsuariosServices"; // Asegúrate de que el path sea correcto
import { ROLES } from "../environment/index"; // Asegúrate de que los roles estén definidos correctamente
import { GestionUsuariosContext } from "../providers/GestionUsuariosProvider";

const CrearUsuario = () => {
  const { setRefreshTrigger, setIsDialogOpen ,setCreatOpen } = useContext(GestionUsuariosContext);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSaveUser = async (e) => {

    e.preventDefault();
    
    
		const formData = new FormData(e.target);
		const userData = {
			nombre: formData.get('nombre'),
			nombre_usuario: formData.get('nombre_usuario'),
			contrasena: formData.get('contrasena'),
			correo: formData.get('correo'),
			rol: formData.get('rol'),
			periodo: 1, // Ajustar según necesidad
			anio: 2024, // Ajustar según necesidad
		};
    

		try {
			// Crear usuario
			const newUser = await createUser(userData);

			// Verificar respuesta
			if (newUser && newUser.id) {
				// Comprobamos que newUser tiene el formato esperado
				alert('Usuario creado exitosamente');
				setRefreshTrigger((prev) => prev + 1);
        setIsDialogOpen(false);
         // Fuerza la actualización de usuarios
			} else {
				console.error('Respuesta inesperada al crear usuario:', newUser);
				alert('Error al crear usuario: Respuesta inesperada del servidor.');
			}
		} catch (error) {
			console.error('Error al crear usuario:', error);
			alert('Hubo un problema al crear el usuario.');
		} finally {
			setIsDialogOpen(false);
		}
	};

  const handleCreateUserClose = () => {
		setCreatOpen(false)
		setIsDialogOpen(false);
	};

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
        <div className="bg-white p-6 rounded-lg w-full max-w-md">
          <h2 className="text-xl font-bold mb-4 text-indigo-700">
            Crear Nuevo Usuario
          </h2>
          <form onSubmit={handleSaveUser} className="space-y-4">
            <div>
              <label htmlFor="nombre" className="block mb-1 text-gray-700">
                Nombre
              </label>
              <input
                id="nombre"
                name="nombre"
                required
                className="w-full border rounded px-3 py-2 text-gray-700"
              />
            </div>
            <div>
              <label htmlFor="nombre_usuario" className="block mb-1 text-gray-700">
                Nombre de Usuario
              </label>
              <input
                id="nombre_usuario"
                name="nombre_usuario"
                required
                className="w-full border rounded px-3 py-2 text-gray-700"
              />
            </div>
            <div>
              <label htmlFor="contrasena" className="block mb-1 text-gray-700">
                Contraseña
              </label>
              <input
                id="contrasena"
                name="contrasena"
                type="password"
                required
                className="w-full border rounded px-3 py-2 text-gray-700"
              />
            </div>
            <div>
              <label htmlFor="correo" className="block mb-1 text-gray-700">
                Correo
              </label>
              <input
                id="correo"
                name="correo"
                type="email"
                required
                className="w-full border rounded px-3 py-2 text-gray-700"
              />
            </div>
            <div>
              <label htmlFor="rol" className="block mb-1 text-gray-700">
                Rol
              </label>
              <select
                id="rol"
                name="rol"
                className="w-full border rounded px-3 py-2 text-gray-700"
                defaultValue="Estudiante"
              >
                {ROLES.map((role) => (
                  <option key={role.value} value={role.value}>
                    {role.label}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label htmlFor="periodo" className="block mb-1 text-gray-700">
                Periodo
              </label>
              <input
                id="periodo"
                name="periodo"
                required
                className="w-full border rounded px-3 py-2 text-gray-700"
                placeholder="Ejemplo: 2024-1"
              />
            </div>
            <div>
              <label htmlFor="año" className="block mb-1 text-gray-700">
                Año
              </label>
              <input
                id="año"
                name="año"
                type="number"
                required
                className="w-full border rounded px-3 py-2 text-gray-700"
                placeholder="Ejemplo: 2024"
              />
            </div>
            <div className="flex justify-end space-x-2">
              <button
                type="button"
                onClick={() => handleCreateUserClose()}
                className="px-4 py-2 border rounded hover:bg-gray-100 text-gray-700"
              >
                Cancelar
              </button>
              <button
                type="submit"
                className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600"
              >
                Crear Usuario
              </button>
            </div>
          </form>
        </div>
      </div>
  );
};

export default CrearUsuario;
