import React from 'react';
import SideNav from '../../components/SideNav';
import { Outlet } from 'react-router-dom';
import Notificaciones from '../../components/Notificaciones';
import InteractiveMap from '../../components/InteractiveMap';

const Home = () => {
  return (
    <div className="flex 100vh 100wh h-full w-full">
			<SideNav />
			<div className="flex justify-center w-full h-full items-center">
				
			</div>
		</div>
  );
};

export default Home;
