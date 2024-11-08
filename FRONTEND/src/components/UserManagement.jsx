'use client'

import React, { useState } from 'react'
import { PencilIcon, TrashIcon, PlusIcon } from '@heroicons/react/24/outline'

export default function UserManagement() {
  const [users, setUsers] = useState([
    { id: 1, nombre: 'Juan Pérez', correo: 'juan@example.com', rol: 'admin' },
    { id: 2, nombre: 'María García', correo: 'maria@example.com', rol: 'user' },
  ])
  const [editingUser, setEditingUser] = useState(null)
  const [isDialogOpen, setIsDialogOpen] = useState(false)

  const handleSaveUser = (e) => {
    e.preventDefault()
    const formData = new FormData(e.target)
    const userData = {
      id: editingUser ? editingUser.id : users.length + 1,
      nombre: formData.get('nombre'),
      correo: formData.get('correo'),
      rol: formData.get('rol'),
    }

    if (editingUser) {
      setUsers(users.map(user => user.id === editingUser.id ? userData : user))
    } else {
      setUsers([...users, userData])
    }

    setIsDialogOpen(false)
    setEditingUser(null)
  }

  const handleDeleteUser = (id) => {
    setUsers(users.filter(user => user.id !== id))
  }

  const handleEditUser = (user) => {
    setEditingUser(user)
    setIsDialogOpen(true)
  }

  return (
    <div className="p-4 space-y-4 bg-gray-100 h-full w-4/5">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-indigo-700">Gestión de Usuarios</h1>
        <button
          onClick={() => {
            setEditingUser(null)
            setIsDialogOpen(true)
          }}
          className="bg-indigo-500 text-white px-4 py-2 rounded hover:bg-indigo-600 flex items-center"
        >
          <PlusIcon className="w-5 h-5 mr-2" />
          Nuevo Usuario
        </button>
      </div>

      {isDialogOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg w-full max-w-md">
            <h2 className="text-xl font-bold mb-4 text-indigo-700">
              {editingUser ? 'Editar Usuario' : 'Crear Nuevo Usuario'}
            </h2>
            <form onSubmit={handleSaveUser} className="space-y-4">
              <div>
                <label htmlFor="nombre" className="block mb-1 text-gray-700">Nombre</label>
                <input
                  id="nombre"
                  name="nombre"
                  defaultValue={editingUser?.nombre}
                  required
                  className="w-full border rounded px-3 py-2 text-gray-700"
                />
              </div>
              <div>
                <label htmlFor="correo" className="block mb-1 text-gray-700">Correo</label>
                <input
                  id="correo"
                  name="correo"
                  type="email"
                  defaultValue={editingUser?.correo}
                  required
                  className="w-full border rounded px-3 py-2 text-gray-700"
                />
              </div>
              <div>
                <label htmlFor="rol" className="block mb-1 text-gray-700">Rol</label>
                <select
                  id="rol"
                  name="rol"
                  defaultValue={editingUser?.rol || 'user'}
                  className="w-full border rounded px-3 py-2 text-gray-700"
                >
                  <option value="admin">Administrador</option>
                  <option value="user">Usuario</option>
                </select>
              </div>
              <div className="flex justify-end space-x-2">
                <button
                  type="button"
                  onClick={() => setIsDialogOpen(false)}
                  className="px-4 py-2 border rounded hover:bg-gray-100 text-gray-700"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-indigo-500 text-white rounded hover:bg-indigo-600"
                >
                  {editingUser ? 'Guardar Cambios' : 'Crear Usuario'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      <table className="min-w-full bg-white shadow-md rounded">
        <thead>
          <tr className="bg-indigo-500 text-white uppercase text-sm leading-normal">
            <th className="py-3 px-6 text-left">ID</th>
            <th className="py-3 px-6 text-left">Nombre</th>
            <th className="py-3 px-6 text-left">Correo</th>
            <th className="py-3 px-6 text-left">Rol</th>
            <th className="py-3 px-6 text-center">Acciones</th>
          </tr>
        </thead>
        <tbody className="text-gray-600 text-sm font-light">
          {users.map((user) => (
            <tr key={user.id} className="border-b border-gray-200 hover:bg-gray-50">
              <td className="py-3 px-6 text-left whitespace-nowrap">{user.id}</td>
              <td className="py-3 px-6 text-left">{user.nombre}</td>
              <td className="py-3 px-6 text-left">{user.correo}</td>
              <td className="py-3 px-6 text-left">{user.rol}</td>
              <td className="py-3 px-6 text-center">
                <button
                  onClick={() => handleEditUser(user)}
                  className="text-indigo-600 hover:text-indigo-900 mr-2"
                >
                  <PencilIcon className="w-5 h-5" />
                </button>
                <button
                  onClick={() => handleDeleteUser(user.id)}
                  className="text-red-600 hover:text-red-900"
                >
                  <TrashIcon className="w-5 h-5" />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}