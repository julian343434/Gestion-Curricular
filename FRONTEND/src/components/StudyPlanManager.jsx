import React, { useState } from 'react'
import { PlusIcon, TrashIcon } from '@heroicons/react/24/outline'

export default function StudyPlanManager() {
  const [semesters, setSemesters] = useState([])
  const [newSubject, setNewSubject] = useState({ name: '', credits: 0 })
  const [selectedSemester, setSelectedSemester] = useState('')

  const addSemester = () => {
    const newSemester = {
      id: Math.random().toString(36).substr(2, 9),
      number: semesters.length + 1,
      subjects: []
    }
    setSemesters([...semesters, newSemester])
  }

  const addSubject = () => {
    if (!selectedSemester || !newSubject.name) return

    setSemesters(semesters.map(semester => {
      if (semester.id === selectedSemester) {
        return {
          ...semester,
          subjects: [...semester.subjects, {
            id: Math.random().toString(36).substr(2, 9),
            ...newSubject
          }]
        }
      }
      return semester
    }))

    setNewSubject({ name: '', credits: 0 })
  }

  const removeSubject = (semesterId, subjectId) => {
    setSemesters(semesters.map(semester => {
      if (semester.id === semesterId) {
        return {
          ...semester,
          subjects: semester.subjects.filter(subject => subject.id !== subjectId)
        }
      }
      return semester
    }))
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="sm:flex sm:items-center">
        <div className="sm:flex-auto">
          <h1 className="text-base font-semibold leading-6 text-gray-900">Plan de Estudio</h1>
          <p className="mt-2 text-sm text-gray-700">
            Gestiona tus semestres y materias para tu plan de estudio.
          </p>
        </div>
        <div className="mt-4 sm:ml-16 sm:mt-0 sm:flex-none">
          <button
            type="button"
            onClick={addSemester}
            className="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          >
            Agregar Semestre
          </button>
        </div>
      </div>

      <div className="mt-8 flow-root">
        <div className="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
          <div className="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
            <div className="overflow-hidden shadow ring-1 ring-black ring-opacity-5 sm:rounded-lg">
              <div className="bg-white px-4 py-5 sm:p-6">
                <div className="grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-6">
                  <div className="sm:col-span-2">
                    <label htmlFor="semester" className="block text-sm font-medium leading-6 text-gray-900">
                      Semestre
                    </label>
                    <select
                      id="semester"
                      name="semester"
                      className="mt-2 block w-full rounded-md border-0 py-1.5 pl-3 pr-10 text-gray-900 ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-indigo-600 sm:text-sm sm:leading-6"
                      value={selectedSemester}
                      onChange={(e) => setSelectedSemester(e.target.value)}
                    >
                      <option value="">Seleccionar semestre</option>
                      {semesters.map(semester => (
                        <option key={semester.id} value={semester.id}>
                          Semestre {semester.number}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="sm:col-span-2">
                    <label htmlFor="subject-name" className="block text-sm font-medium leading-6 text-gray-900">
                      Nombre de la materia
                    </label>
                    <input
                      type="text"
                      name="subject-name"
                      id="subject-name"
                      className="mt-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                      value={newSubject.name}
                      onChange={(e) => setNewSubject({ ...newSubject, name: e.target.value })}
                    />
                  </div>
                  <div className="sm:col-span-1">
                    <label htmlFor="credits" className="block text-sm font-medium leading-6 text-gray-900">
                      Créditos
                    </label>
                    <input
                      type="number"
                      name="credits"
                      id="credits"
                      className="mt-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                      value={newSubject.credits || ''}
                      onChange={(e) => setNewSubject({ ...newSubject, credits: parseInt(e.target.value) || 0 })}
                    />
                  </div>
                  <div className="sm:col-span-1">
                    <button
                      type="button"
                      onClick={addSubject}
                      className="mt-8 inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                    >
                      <PlusIcon className="-ml-0.5 mr-1.5 h-5 w-5" aria-hidden="true" />
                      Agregar
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="mt-8 grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {semesters.map(semester => (
          <div key={semester.id} className="bg-white overflow-hidden shadow rounded-lg divide-y divide-gray-200">
            <div className="px-4 py-5 sm:px-6">
              <h3 className="text-lg leading-6 font-medium text-gray-900">Semestre {semester.number}</h3>
            </div>
            <div className="px-4 py-5 sm:p-6">
              <ul role="list" className="divide-y divide-gray-200">
                {semester.subjects.map(subject => (
                  <li key={subject.id} className="py-4 flex items-center justify-between">
                    <div className="flex flex-col">
                      <p className="text-sm font-medium text-gray-900">{subject.name}</p>
                      <p className="text-sm text-gray-500">{subject.credits} créditos</p>
                    </div>
                    <button
                      type="button"
                      onClick={() => removeSubject(semester.id, subject.id)}
                      className="inline-flex items-center rounded-full bg-white px-2.5 py-1 text-xs font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                    >
                      <TrashIcon className="mr-1.5 h-5 w-5 text-gray-400" aria-hidden="true" />
                      Eliminar
                    </button>
                  </li>
                ))}
              </ul>
              {semester.subjects.length === 0 && (
                <p className="text-sm text-gray-500 text-center py-4">No hay materias en este semestre</p>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}