'use client';

import React, { useState, useEffect } from 'react';
import {
	PencilIcon,
	TrashIcon,
	PlusIcon,
	CheckCircleIcon,
} from '@heroicons/react/24/outline';
import {
	createUser,
	getUsers,
	eliminarUsuario,
	activarUsuario,
} from '../services/GestionUsuariosServices'; // Asegúrate de que las rutas sean correctas

// Lista de roles mapeada a partir de tu base de datos
const ROLES = [
	{ value: 'Administrador', label: 'Administrador' },
	{ value: 'Profesor de Comite', label: 'Profesor de Comité' },
	{ value: 'Profesor', label: 'Profesor' },
	{ value: 'Estudiante', label: 'Estudiante' },
];

export default function UserManagement() {
	const [users, setUsers] = useState([]);
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const [refreshTrigger, setRefreshTrigger] = useState(0);
  const [userToEdit, setUserToEdit] = useState(null)

	// Obtener usuarios desde el servicio
	useEffect(() => {
		const fetchUsers = async () => {
			try {
				const data = await getUsers(); // Llamada al servicio
				const formattedUsers = data.map((user) => ({
					id: user.id,
					nombre: user.nombre,
					correo: user.correo,
					rol: user.rol[0]?.rol.nombre || 'Sin rol', // Asume el primer rol de la lista
					activo: user.activo,
				}));
				setUsers(formattedUsers);
			} catch (error) {
				console.error('Error al obtener usuarios:', error);
				alert('Hubo un problema al cargar los usuarios.');
			}
		};
		fetchUsers();
	}, [refreshTrigger]); // Se ejecuta cada vez que refreshTrigger cambia

	// Crear nuevo usuario
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
				setRefreshTrigger((prev) => prev + 1); // Fuerza la actualización de usuarios
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

	// Eliminar usuario
	const handleDeleteUser = async (userId) => {
		const token = localStorage.getItem('token'); // Asegúrate de guardar el token al autenticarte

		if (!token) {
			alert('No estás autenticado.');
			return;
		}

		const confirmDelete = confirm(
			'¿Estás seguro de que deseas eliminar este usuario?'
		);
		if (!confirmDelete) return;

		try {
			const wasDeleted = await eliminarUsuario(userId, token);
			if (wasDeleted) {
				alert('Usuario eliminado correctamente.');
				setRefreshTrigger((prev) => prev + 1); // Fuerza la actualización
			}
		} catch (error) {
			alert('Hubo un problema al eliminar el usuario.');
		}
	};

	const handleActivateUser = async (userId) => {
		const token = localStorage.getItem('token'); // Asegúrate de guardar el token al autenticarte

		if (!token) {
			alert('No estás autenticado.');
			return;
		}

		const confirmDelete = confirm(
			'¿Estás seguro de que deseas activar este usuario?'
		);
		if (!confirmDelete) return;

		try {
			const wasDeleted = await activarUsuario(userId, token);
			if (wasDeleted) {
				alert('Usuario activado correctamente.');
				setRefreshTrigger((prev) => prev + 1); // Fuerza la actualización
			}
		} catch (error) {
			alert('Hubo un problema al activar el usuario.');
		}
	};

  const handleEditUser = (user) => {
    setUserToEdit(user);
    setIsEditDialogOpen(true);
  };
  

	return (
		<div className="p-4 space-y-4 bg-gray-100 h-full w-4/5">
			<div className="flex justify-between items-center">
				<h1 className="text-2xl font-bold text-indigo-700">
					Gestión de Usuarios
				</h1>
				<button
					onClick={() => setIsDialogOpen(true)}
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
							Crear Nuevo Usuario
						</h2>
						<form onSubmit={handleSaveUser} className="space-y-4">
							<div>
								<label
									htmlFor="nombre"
									className="block mb-1 text-gray-700"
								>
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
								<label
									htmlFor="nombre_usuario"
									className="block mb-1 text-gray-700"
								>
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
								<label
									htmlFor="contrasena"
									className="block mb-1 text-gray-700"
								>
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
								<label
									htmlFor="correo"
									className="block mb-1 text-gray-700"
								>
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
									Crear Usuario
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
						<th className="py-3 px-6 text-left">Estado</th>
						<th className="py-3 px-6 text-center">Acciones</th>
					</tr>
				</thead>
				<tbody className="text-gray-600 text-sm font-light">
					{users.map((user) => (
						<tr
							key={user.id}
							className="border-b border-gray-200 hover:bg-gray-50"
						>
							<td className="py-3 px-6 text-left whitespace-nowrap">
								{user.id}
							</td>
							<td className="py-3 px-6 text-left">{user.nombre}</td>
							<td className="py-3 px-6 text-left">{user.correo}</td>
							<td className="py-3 px-6 text-left">{user.rol}</td>
							<td className="py-3 px-6 text-left">
								{user.activo ? 'Activo' : 'Inactivo'}
							</td>
							<td className="py-3 px-6 text-center">
								<button
									onClick={() => handleEditUser(user)}
									className="text-indigo-600 hover:text-indigo-900 mr-2"
								>
									<PencilIcon className="w-5 h-5" />
								</button>
								<button
									onClick={() => handleDeleteUser(user.id)}
									className="text-red-600 hover:text-red-900 mr-2"
								>
									<TrashIcon className="w-5 h-5" />
								</button>
								
									<button
										onClick={() => handleActivateUser(user.id)}
										className="text-green-600 hover:text-green-900"
									>
										<CheckCircleIcon className="w-5 h-5" />
									</button>
								
							</td>
						</tr>
					))}
				</tbody>
			</table>
		</div>
	);
}
