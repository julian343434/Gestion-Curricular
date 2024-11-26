import React from 'react';

export default function PlanEstudioActual({ currentPlan, onModify }) {
  if (!currentPlan) {
    return (
      <div className="bg-white shadow rounded-lg p-6 mb-8">
        <h2 className="text-2xl font-bold mb-4 text-gray-800">Plan de Estudios Actual</h2>
        <p className="text-gray-700 text-lg">No hay un plan de estudios cargado actualmente.</p>
      </div>
    );
  }

  return (
    <div className="bg-white shadow rounded-lg p-6 mb-8">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">Plan de Estudios Actual</h2>
      <div className="space-y-3">
        <p className="text-lg text-gray-900">
          <span className="font-medium">Nombre:</span> {currentPlan.name}
        </p>
        <p className="text-lg text-gray-900">
          <span className="font-medium">Descripción:</span> {currentPlan.description}
        </p>
        <p className="text-lg text-gray-900">
          <span className="font-medium">Año:</span> {currentPlan.year}
        </p>
        <p className="text-lg text-gray-900">
          <span className="font-medium">Archivo:</span> {currentPlan.file}
        </p>
      </div>
      <button
        onClick={() => onModify(currentPlan)}
        className="mt-4 inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
      >
        Modificar
      </button>
    </div>
  );
}
