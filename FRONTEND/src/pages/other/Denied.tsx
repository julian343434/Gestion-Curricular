import { Button } from '@nextui-org/react';
import { useNavigate } from 'react-router-dom';

const DeniedPage: React.FC = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/');
    };

    return (
        <div>
            <h1>Página de acceso negado o no encontrada :(</h1>
            <p>Por favor, inicie sesión para acceder a esta página</p>
            <Button 
                color="primary"
                className="mt-2"
                onClick={handleLogin}
            >
                Login
            </Button>
        </div>
    );
};

export default DeniedPage;
