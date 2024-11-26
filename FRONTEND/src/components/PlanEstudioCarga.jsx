import React, { useState, useContext } from 'react';
import { HiOutlineDocumentAdd, HiOutlinePencil, HiOutlineCalendar } from 'react-icons/hi';
import { GestionPlanEstudioContext } from '../providers/GestionPlanEstudioProvider';
import { uploadExcelFile } from '../services/GestionPlanEstudioServices'; // Importar la función uploadExcelFile

export default function PlanEstudioCarga() { // Asegúrate de pasar el token a través de props o contexto
  const [file, setFile] = useState(null);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [year, setYear] = useState('');
  const [showForm, setShowForm] = useState(false); // Estado para mostrar el formulario

  const { setIsDialogOpen, isDialogOpen, setIsFormSubmitted } = useContext(
    GestionPlanEstudioContext
  );

  const token = localStorage.getItem("token")

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (file && name && year && token) {
      try {
        // Llamamos a la función uploadExcelFile para cargar el archivo y los datos
        const response = await uploadExcelFile(file, name, description, year, token);

        // Si la carga fue exitosa, reseteamos los campos
        if (response) {
          setFile(null);
          setName('');
          setDescription('');
          setYear('');
          setIsDialogOpen(false); // Cerrar el diálogo al enviar el formulario
          setShowForm(false); // Ocultar el formulario después de enviarlo
          console.log('Archivo cargado exitosamente:', response);
        }
      } catch (error) {
        console.error('Error al cargar el archivo:', error);
      }
    } else {
      console.error('Faltan campos o el token no es válido');
    }
  };

  return (
    <div className="justify-center h-full w-full">
      <div className="flex justify-center w-full shadow-lg rounded-lg p-6 mb-8 bg-white dark:bg-gray-800">
        {/* Mostrar solo el botón inicialmente */}
        {!showForm ? (
          <button
            onClick={() => {
              setShowForm(true);
              setIsDialogOpen(true); // Mostrar el formulario al hacer clic
            }}
            className="w-full sm:w-1/2 bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 flex items-center justify-center gap-3 transition-all duration-300"
          >
            <HiOutlineDocumentAdd /> Cargar Plan de Estudios
          </button>
        ) : (
          <form
            onSubmit={handleSubmit}
            className="space-y-6 mt-4 w-full max-w-lg mx-auto"
          >
            <h2 className="text-3xl font-semibold mb-6 text-white dark:text-black">
              Cargar Plan de Estudios
            </h2>

            {/* Campo de Archivo */}
            <div>
              <label
                htmlFor="file"
                className="block text-sm font-medium text-gray-900 dark:text-gray-100 flex items-center gap-2"
              >
                <HiOutlineDocumentAdd className="text-gray-500" /> Archivo
                Excel
              </label>
              <input
                type="file"
                id="file"
                accept=".xlsx, .xls"
                onChange={(e) => setFile(e.target.files[0])}
                className="mt-2 block w-full border text-black border-gray-300 rounded-lg shadow-sm p-3 bg-gray-100 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Campo de Nombre */}
            <div>
              <label
                htmlFor="name"
                className="block text-sm font-medium text-gray-900 dark:text-gray-100 flex items-center gap-2"
              >
                <HiOutlinePencil className="text-gray-500" /> Nombre del
                Plan
              </label>
              <input
                type="text"
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="mt-2 block w-full border text-black border-gray-300 rounded-lg shadow-sm p-3 bg-gray-100 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Campo de Descripción */}
            <div>
              <label
                htmlFor="description"
                className="block text-sm font-medium text-gray-900 dark:text-gray-100 flex items-center gap-2"
              >
                <HiOutlinePencil className="text-gray-500" /> Descripción
              </label>
              <textarea
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="mt-2 block w-full border text-black border-gray-300 rounded-lg shadow-sm p-3 bg-gray-100 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              ></textarea>
            </div>

            {/* Campo de Año */}
            <div>
              <label
                htmlFor="year"
                className="block text-sm font-medium text-gray-900 dark:text-gray-100 flex items-center gap-2"
              >
                <HiOutlineCalendar className="text-gray-500" /> Año del
                Plan
              </label>
              <input
                type="number"
                id="year"
                value={year}
                onChange={(e) => setYear(e.target.value)}
                className="mt-2 block w-full border text-black border-gray-300 rounded-lg shadow-sm p-3 bg-gray-100 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Botón de Enviar */}
            <button
              type="submit"
              className="w-full bg-blue-600 text-black p-3 rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 flex items-center justify-center gap-3 transition-all duration-300"
            >
              <HiOutlineDocumentAdd /> Cargar Plan de Estudios
            </button>
          </form>
        )}
      </div>
    </div>
  );
}
