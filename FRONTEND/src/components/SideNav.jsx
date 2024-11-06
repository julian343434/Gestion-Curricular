import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FaImages, FaVideo, FaUsers, FaSignOutAlt, FaCog, FaBars } from 'react-icons/fa';
import { Button } from "@nextui-org/button";

const SideNav = () => {
    const [isOpen, setIsOpen] = useState(true);
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/');
    };

    return (
        <div 
            className={
                `lef-column top-0 left-0 h-screen bg-gray-900 text-white p-4 ${isOpen ? 'w-64' : 'w-16'} duration-300 ease-in-out`
                }>
            <button 
                className="text-white mb-4"
                onClick={() => setIsOpen(!isOpen)}
            >
                <FaBars />
            </button>
            <div className="flex flex-col justify-between h-full pb-6">
                <div className="flex flex-col gap-4 mt-2">
                    <Link to="/user-admin/crear-alertas" className="flex items-center gap-2 p-2 hover:bg-gray-700 rounded-md">
                        <FaUsers /> {isOpen && <span className="text-white">crear alertas</span>}
                    </Link>
                    
                    
                </div>
                <div className="flex flex-col gap-2">
                    <Button 
                        onClick={handleLogout} 
                        className={
                            `flex items-center gap-2 bg-orange-500 hover:bg-orange-600 rounded-md p-2 ${isOpen ? 'w-full' : 'w-12'}`
                        }
                    >
                        <FaSignOutAlt 
                            className="text-white"
                        /> {isOpen && <span className="text-white">Cerrar sesión</span>}
                    </Button>
                    <Link to="/dashboard/settings" className="flex items-center gap-2 p-2 hover:bg-gray-700 rounded-md">
                        <FaCog /> {isOpen && <span>Configuración</span>}
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default SideNav;
