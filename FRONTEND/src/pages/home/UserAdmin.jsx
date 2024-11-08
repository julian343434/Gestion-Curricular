import React, { useState } from 'react';
import AdminSideNav from '../../components/AdminSideNav';
import StudyPlanManager from '../../components/StudyPlanManager';
import UserManagement from '../../components/UserManagement'
import HomeFacultad from '../../components/HomeFacultad';
const UserAdmin = () => {
	const [vistaActual, setVistaActual] = useState(''); // Estado para la vista actual

	// Función para renderizar el contenido dinámico
	const renderVista = () => {
		switch (vistaActual) {
			case 'GestionPlanEstudio':
				return <StudyPlanManager />;
				
			case 'settings':
				return <div>Configuración de usuario</div>;
			case 'GestionUsuarios':
				return <UserManagement/>;
			default:
				return <HomeFacultad/>;
		}
	};

	return (
		<div className="flex h-screen w-screen">
			<AdminSideNav setVistaActual={setVistaActual} />{' '}
			{/* Pasar la función de cambio de vista */}
			<div className="flex justify-center w-full h-full items-center bg-slate-100">
				{renderVista()} {/* Renderizar el contenido basado en la vista actual */}
			</div>
		</div>
	);
};

export default UserAdmin;
