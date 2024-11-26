import React, { useState } from "react";
import { uploadExcelFile } from "../services/uploadService"; // Asegúrate de crear esta función en tu archivo de servicios.

const UploadExcelButton = () => {
  const [file, setFile] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const token = localStorage.getItem('token'); // Reemplaza con tu token JWT

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) {
      alert("Por favor selecciona un archivo antes de subirlo.");
      return;
    }

    setIsLoading(true);

    try {
      const response = await uploadExcelFile(
        file,
        "Plan Prueba",
        "Primer plan creado",
        2024,
        token
      );
      console.log("Archivo subido con éxito:", response);
      alert("Archivo subido con éxito.");
    } catch (error) {
      console.error("Error al subir el archivo:", error);
      alert("Error al subir el archivo.");
    } finally {
      setIsLoading(false);
      setFile(null);
    }
  };

  return (
    <div className="flex flex-col items-center gap-4 p-4">
      <input
        type="file"
        accept=".xlsx"
        onChange={handleFileChange}
        className="block w-full text-sm text-gray-500
                   file:mr-4 file:py-2 file:px-4
                   file:rounded file:border-0
                   file:text-sm file:font-semibold
                   file:bg-blue-50 file:text-blue-700
                   hover:file:bg-blue-100"
      />
      <button
        onClick={handleUpload}
        disabled={isLoading}
        className={`px-6 py-2 rounded text-white font-semibold ${
          isLoading
            ? "bg-gray-400 cursor-not-allowed"
            : "bg-blue-500 hover:bg-blue-600"
        }`}
      >
        {isLoading ? "Subiendo..." : "Subir Archivo"}
      </button>
    </div>
  );
};

export default UploadExcelButton;
