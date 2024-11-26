import React, { useState } from 'react';
import { HiOutlineDocumentAdd, HiOutlinePencil, HiOutlineCalendar } from 'react-icons/hi'; // Iconos de react-icons

export default function PlanEstudioCarga({ onUpload }) {
  const [file, setFile] = useState(null);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [year, setYear] = useState('');
  const [showForm, setShowForm] = useState(false); // Estado para mostrar el formulario

  const handleSubmit = (e) => {
    e.preventDefault();
    if (file && name && year) {
      onUpload({ file: file.name, name, description, year });
      setFile(null);
      setName('');
      setDescription('');
      setYear('');
      setShowForm(false); // Ocultar el formulario después de enviarlo
    }
  };

  return (
    <div className='justify-center ite'>

    <div className="flex justify-center w-full  shadow rounded-lg p-6 mb-8">
      {/* Mostrar solo el botón inicialmente */}
      {!showForm ? (
          <button
          onClick={() => setShowForm(true)} // Mostrar el formulario al hacer clic
          className="w-1/2 bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 flex items-center justify-center gap-2"
          >
          <HiOutlineDocumentAdd /> Cargar Plan de Estudios
        </button>
      ) : (
          <form onSubmit={handleSubmit} className="space-y-6 mt-4 w-full max-w-md mx-auto">
          <h2 className="text-2xl font-bold mb-4 text-gray-800">Cargar Plan de Estudios</h2>

          {/* Campo de Archivo */}
          <div>
            <label htmlFor="file" className="block text-sm font-medium text-gray-700 flex items-center gap-2">
              <HiOutlineDocumentAdd className="text-gray-500" /> Archivo Excel
            </label>
            <input
              type="file"
              id="file"
              accept=".xlsx, .xls"
              onChange={(e) => setFile(e.target.files[0])}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
              />
          </div>

          {/* Campo de Nombre */}
          <div>
            <label htmlFor="name" className="block text-sm font-medium text-gray-700 flex items-center gap-2">
              <HiOutlinePencil className="text-gray-500" /> Nombre del Plan
            </label>
            <input
              type="text"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
              />
          </div>

          {/* Campo de Descripción */}
          <div>
            <label htmlFor="description" className="block text-sm font-medium text-gray-700 flex items-center gap-2">
              <HiOutlinePencil className="text-gray-500" /> Descripción
            </label>
            <textarea
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
              ></textarea>
          </div>

          {/* Campo de Año */}
          <div>
            <label htmlFor="year" className="block text-sm font-medium text-gray-700 flex items-center gap-2">
              <HiOutlineCalendar className="text-gray-500" /> Año del Plan
            </label>
            <input
              type="number"
              id="year"
              value={year}
              onChange={(e) => setYear(e.target.value)}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
              />
          </div>

          {/* Botón de Enviar */}
          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 flex items-center justify-center gap-2"
            >
            <HiOutlineDocumentAdd /> Cargar Plan de Estudios
          </button>
        </form>
      )}
    </div>
      </div>
  );
}
