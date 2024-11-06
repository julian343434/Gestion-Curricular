import React from 'react';
import SideNav from '../../components/SideNav';
import AlertasFormulario from '../../components/AlertasFormulario';
import Notificaciones from '../../components/Notificaciones';

const UserAdmin = () => {
	return (
		<div className="flex 100vh 100wh h-full w-full">
			<SideNav />
			<div className="flex justify-center w-full h-full items-center">
				<Notificaciones></Notificaciones>
			</div>
		</div>
	);
};

export default UserAdmin;
