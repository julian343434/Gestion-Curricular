import React from 'react';
import SideNav from '../../components/SideNav';
import { Outlet } from 'react-router-dom';
import Notificaciones from '../../components/Notificaciones';
import InteractiveMap from '../../components/InteractiveMap';

const Home = () => {
  return (
    <div className="relative flex h-screen w-full justify-center overflow-hidden">
      {/* Mapa Interactivo de fondo */}
      <div className="absolute inset-0 z-0  filter blur-[5px]">
        <InteractiveMap />
      </div>
      
      {/* Contenido superpuesto */}
      <div className="relative flex  w-4/5 z-10">
        {/* Menú lateral */}
        

        {/* Contenido principal */}
        <div className="flex-grow w-4/5 mx-auto  bg-opacity-80 p-6">
          <h1 className="text-pink-600 text-2xl mb-4 font-bold">Bienvenido a Villavicencio</h1>
          <Notificaciones className="text-white" />
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Home;
