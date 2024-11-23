import React, { useState, useContext } from "react";
import { actualizarRolUsuario } from "../services/GestionUsuariosServices"; // Asegúrate de que el path sea correcto
import { ROLES } from "../environment/index"; // Lista de roles
import { GestionUsuariosContext } from "../providers/GestionUsuariosProvider";

const ActualizarUsuario = ({ userToEdit }) => {
  const { setRefreshTrigger , setActualizarOpen , setIsDialogOpen } = useContext(GestionUsuariosContext);
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);


  const [formData, setFormData] = useState({
    nombre: userToEdit?.nombre || "",
    correo: userToEdit?.correo || "",
    rol: userToEdit?.rol || "",
    periodo: userToEdit?.periodo || 1,
    anio: userToEdit?.anio || new Date().getFullYear(),
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSaveChanges = async (e) => {
    e.preventDefault();
    const { rol, periodo, anio } = formData;

    try {
      await actualizarRolUsuario(userToEdit.id, { rol, periodo, anio });
      alert("Usuario actualizado correctamente.");
      setRefreshTrigger((prev) => prev + 1); // Refresca la lista de usuarios
    } catch (error) {
      console.error("Error al actualizar el usuario:", error);
      alert("Hubo un problema al actualizar el usuario.");
    } finally {
      setIsEditDialogOpen(false);
    }
  };

  const handleActualizarUserClose = () => {
    setActualizarOpen(false)
    setIsDialogOpen(false);
};


  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
      <div className="bg-white p-6 rounded-lg w-full max-w-md">
        <h2 className="text-xl font-bold mb-4 text-indigo-700">
          Editar Usuario
        </h2>
        <form onSubmit={handleSaveChanges} className="space-y-4">
          <div>
            <label htmlFor="nombre" className="block mb-1 text-gray-700">
              Nombre
            </label>
            <input
              id="nombre"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              disabled
              className="w-full border rounded px-3 py-2 text-gray-700 bg-gray-100"
            />
          </div>
          <div>
            <label htmlFor="correo" className="block mb-1 text-gray-700">
              Correo
            </label>
            <input
              id="correo"
              name="correo"
              value={formData.correo}
              onChange={handleChange}
              disabled
              className="w-full border rounded px-3 py-2 text-gray-700 bg-gray-100"
            />
          </div>
          <div>
            <label htmlFor="rol" className="block mb-1 text-gray-700">
              Rol
            </label>
            <select
              id="rol"
              name="rol"
              value={formData.rol}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2 text-gray-700"
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
              value={formData.periodo}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2 text-gray-700"
            />
          </div>
          <div>
            <label htmlFor="anio" className="block mb-1 text-gray-700">
              Año
            </label>
            <input
              id="anio"
              name="anio"
              type="number"
              value={formData.anio}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2 text-gray-700"
            />
          </div>
          <div className="flex justify-end space-x-2">
            <button
              type="button"
              onClick={() => handleActualizarUserClose()}
              className="px-4 py-2 border rounded hover:bg-gray-100 text-gray-700"
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600"
            >
              Guardar Cambios
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ActualizarUsuario;
