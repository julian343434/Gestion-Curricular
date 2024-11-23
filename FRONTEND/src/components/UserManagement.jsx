'use client';

import React, { useState, useEffect, useContext } from 'react';
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
import { GestionUsuariosContext } from '../providers/GestionUsuariosProvider';
import CrearUsuario from './CrearUsuario';
import ActualizarUsuario from './ActualizarUsuario';

// Lista de roles mapeada a partir de tu base de datos
const ROLES = [
	{ value: 'Administrador', label: 'Administrador' },
	{ value: 'Profesor de Comite', label: 'Profesor de Comité' },
	{ value: 'Profesor', label: 'Profesor' },
	{ value: 'Estudiante', label: 'Estudiante' },
];

export default function UserManagement() {
	const [users, setUsers] = useState([]);

	const {
		refreshTrigger,
		setRefreshTrigger,
		isDialogOpen,
		setIsDialogOpen,
		createOpen,
		setCreatOpen,
		actualizarOpen,
		setActualizarOpen,
	} = useContext(GestionUsuariosContext);

	const [userToEdit, setUserToEdit] = useState(null);

	// Obtener usuarios desde el servicio
	useEffect(() => {
		const fetchUsers = async () => {
			try {
				const data = await getUsers(); // Llamada al servicio
				const formattedUsers = data.map((user) => ({
					id: user.id,
					nombre: user.nombre,
					correo: user.correo,
					rol: user.rol || 'Sin rol', // Asume el primer rol de la lista
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
		setActualizarOpen(true);
		setIsDialogOpen(true);
		setUserToEdit(user);
		console.log(user);
	};
	const handleCreateUser = () => {
		setCreatOpen(true);
		setIsDialogOpen(true);
	};

	return (
		<div className="p-4 space-y-4 bg-gray-100 h-full w-4/5">
			<div className="flex justify-between items-center">
				<h1 className="text-2xl font-bold text-indigo-700">
					Gestión de Usuarios
				</h1>
				<button
					onClick={() => handleCreateUser()}
					className="bg-indigo-500 text-white px-4 py-2 rounded hover:bg-indigo-600 flex items-center"
				>
					<PlusIcon className="w-5 h-5 mr-2" />
					Nuevo Usuario
				</button>
			</div>

			{isDialogOpen && createOpen && <CrearUsuario />}
			{isDialogOpen && actualizarOpen && (
				<ActualizarUsuario userToEdit={userToEdit} />
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
							<td className="py-3 px-6 text-left">
								{user.rol.length > 0
									? user.rol[user.rol.length - 1]?.rol?.nombre
									: 'Sin rol'}
							</td>
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
