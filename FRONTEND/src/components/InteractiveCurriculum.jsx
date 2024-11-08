import React, { useState } from 'react'
import { motion } from 'framer-motion'
import { Link } from 'react-router-dom'

// Datos de ejemplo (en una aplicación real, estos vendrían de una API o base de datos)
const programs = [
  { id: 1, name: "Ingeniería de Sistemas" },
  { id: 2, name: "Medicina" },
  { id: 3, name: "Derecho" },
]

const semesters = Array.from({ length: 10 }, (_, i) => ({
  id: i + 1,
  name: `Semestre ${i + 1}`,
  subjects: [
    { id: `${i + 1}-1`, name: `Materia 1 del Semestre ${i + 1}` },
    { id: `${i + 1}-2`, name: `Materia 2 del Semestre ${i + 1}` },
    { id: `${i + 1}-3`, name: `Materia 3 del Semestre ${i + 1}` },
    { id: `${i + 1}-4`, name: `Materia 4 del Semestre ${i + 1}` },
  ]
}))

export default function InteractiveCurriculum() {
  const [selectedProgram, setSelectedProgram] = useState(null)
  const [selectedSemester, setSelectedSemester] = useState(null)

  return (
    <div className="w-full h-screen bg-gradient-to-br from-red-100 to-red-200 p-8 relative">
      {/* Enlace de login en la esquina superior derecha */}
      <Link
        to="/login"
        className="absolute top-4 right-4 bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700 transition-colors duration-300"
      >
        Login
      </Link>

      <h1 className="text-4xl font-bold text-center mb-8 text-red-800">Explorador de Plan de Estudios</h1>
      
      {!selectedProgram ? (
        <div className="max-w-2xl mx-auto">
          <h2 className="text-2xl font-semibold mb-4 text-red-700">Selecciona un Programa</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {programs.map((program) => (
              <motion.button
                key={program.id}
                className="bg-white p-6 rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-300 text-left"
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
                onClick={() => setSelectedProgram(program)}
              >
                <h3 className="text-xl font-semibold text-red-600">{program.name}</h3>
                <p className="mt-2 text-gray-600">Explora el plan de estudios</p>
              </motion.button>
            ))}
          </div>
        </div>
      ) : (
        <div>
          <h2 className="text-2xl font-semibold mb-4 text-red-700">{selectedProgram.name}</h2>
          <button
            className="mb-4 text-red-600 hover:text-red-800"
            onClick={() => {
              setSelectedProgram(null)
              setSelectedSemester(null)
            }}
          >
            ← Volver a la selección de programas
          </button>
          
          {!selectedSemester ? (
            <div className="grid grid-cols-2 md:grid-cols-5 gap-4">
              {semesters.map((semester) => (
                <motion.button
                  key={semester.id}
                  className="bg-white p-4 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300"
                  whileHover={{ scale: 1.05 }}
                  whileTap={{ scale: 0.95 }}
                  onClick={() => setSelectedSemester(semester)}
                >
                  <h3 className="text-lg font-semibold text-red-600">{semester.name}</h3>
                  <p className="mt-2 text-sm text-gray-600">{semester.subjects.length} materias</p>
                </motion.button>
              ))}
            </div>
          ) : (
            <div>
              <button
                className="mb-4 text-red-600 hover:text-red-800"
                onClick={() => setSelectedSemester(null)}
              >
                ← Volver a la selección de semestres
              </button>
              <h3 className="text-xl font-semibold mb-4 text-red-700">{selectedSemester.name}</h3>
              <ul className="space-y-4">
                {selectedSemester.subjects.map((subject) => (
                  <motion.li
                    key={subject.id}
                    className="bg-white p-4 rounded-lg shadow-md"
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.3 }}
                  >
                    <h4 className="text-lg font-semibold text-red-600">{subject.name}</h4>
                    <p className="mt-2 text-gray-600">Descripción de la materia...</p>
                  </motion.li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  )
}
