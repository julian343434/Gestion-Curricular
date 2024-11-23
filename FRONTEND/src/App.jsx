import { NextUIProvider } from '@nextui-org/react';
import { AuthProvider } from './providers/AuthProvider';
import ContainerNavigation from './navigation/Index';
import MainLayout from './layouts/MainLayout';
import { GestionUsuariosProvider } from './providers/GestionUsuariosProvider';
function App() {
	return (
		<NextUIProvider>
			<AuthProvider>
				<GestionUsuariosProvider>
					<MainLayout>
						<ContainerNavigation />
					</MainLayout>
				</GestionUsuariosProvider>
			</AuthProvider>
		</NextUIProvider>
	);
}

export default App;
