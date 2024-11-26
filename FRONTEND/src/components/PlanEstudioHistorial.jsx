import React from 'react';

export default function StudyPlanHistory({ planHistory = [], onModify }) {
  return (
    <div className="bg-white shadow rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">Historial de Planes de Estudio</h2>
      {planHistory.length === 0 ? (
        <p className="text-gray-700 text-lg">No hay planes de estudio en el historial.</p>
      ) : (
        <ul className="divide-y divide-gray-300">
          {planHistory.map((plan, index) => (
            <li key={index} className="py-4">
              <div className="flex justify-between items-center">
                <div>
                  <p className="text-lg font-semibold text-gray-900">{plan.name}</p>
                  <p className="text-gray-700">
                    <span className="font-medium">Año:</span> {plan.year}
                  </p>
                  <p className="text-gray-700">
                    <span className="font-medium">Archivo:</span> {plan.file}
                  </p>
                </div>
                <button
                  onClick={() => onModify(plan)}
                  className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                  Modificar
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
