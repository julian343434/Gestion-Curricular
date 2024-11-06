import { Button } from '@nextui-org/react';
import { useNavigate } from 'react-router-dom';

const DeniedPage = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/');
    };

    return (
        <div>
            <h1>Pagina de acesso negado o no encontrada :( </h1>
            <p>Por favor, inicie sesion para acceder a esta pagina</p>
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