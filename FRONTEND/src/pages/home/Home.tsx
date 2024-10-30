
import { Outlet } from 'react-router-dom';


const Home = () => {
  return (
    <div className="relative flex h-screen w-full justify-center overflow-hidden">
      hola
      <Outlet />
    </div>

  );
};

export default Home;
