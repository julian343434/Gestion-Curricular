import React, { useState } from 'react';
import {
	Button,
	Input,
	Select,
	SelectItem,
	Checkbox,
	Card,
	CardBody,
	CardHeader,
} from '@nextui-org/react';

export default function RegisterPage() {
	const [userData, setUserData] = useState({
		numeroCel: '',
		tipoVehiculo: '',
		placa: '',
		categoria: '',
	});

	// Arreglos de valores para los Selects
	const valoresSelecTipo = ['automovil', 'moto', 'taxi'];
	const valoresSelecCategoria = ['Privado', 'Publico', 'Permiso Especial'];

	const handleInputChange = (e) => {
		setUserData({ ...userData, [e.target.name]: e.target.value });
	};

	const handleSelectChange = (field, value) => {
		setUserData({ ...userData, [field]: value });
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		const { numeroCel, tipoVehiculo, placa, categoria } = userData;

		const requestBody = {
			numero_celular: numeroCel,
			tipo_vehiculo: valoresSelecTipo[tipoVehiculo],
			placa: placa,
			categoria: valoresSelecCategoria[categoria],
		};

		try {
			const response = await fetch('http://localhost:3001/api/subscribe', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(requestBody),
			});

			if (response.ok) {
				console.log('Solicitud exitosa', await response.json());
			} else {
				console.error('Error en la solicitud', response.statusText);
			}
		} catch (error) {
			console.error('Error de red', error);
		}
	};

	return (
		<div className="container mx-auto p-4">
			<Card className="max-w-2xl mx-auto bg-pink-50 shadow-lg"> {/* Fondo pastel rosa */}
				<CardHeader className="flex flex-col items-start px-6 py-4">
					<h1 className="text-2xl font-bold text-pink-600">Suscripción a servicio</h1> {/* Título en rosa más oscuro */}
					<p className="text-pink-500"> {/* Texto en un tono pastel rosa */}
						Regístrate para recibir alertas de movilidad en Villavicencio
					</p>
				</CardHeader>
				<CardBody>
					<form onSubmit={handleSubmit} className="space-y-6">
						<Input
							label="Número de Celular"
							name="numeroCel"
							type="tel"
							placeholder="Ej: 3001234567"
							value={userData.numeroCel}
							onChange={handleInputChange}
							required
							className="bg-pink-100" // Color pastel para el input
						/>
	
						<Select
							label="Tipo de Vehículo"
							placeholder="Selecciona un tipo"
							value={userData.tipoVehiculo}
							onChange={(e) => handleSelectChange('tipoVehiculo', e.target.value)}
							required
							className="bg-pink-100" // Color pastel para el select
						>
							{valoresSelecTipo.map((tipo, index) => (
								<SelectItem className=' bg-pink-500' key={index} value={tipo}>
									{tipo.charAt(0).toUpperCase() + tipo.slice(1)}
								</SelectItem>
							))}
						</Select>
	
						<Input
							label="Placa del Vehículo"
							name="placa"
							placeholder="Ej: ABC123"
							value={userData.placa}
							onChange={handleInputChange}
							required
							className="bg-pink-100" // Color pastel para el input
						/>
	
						<Select
							label="Categoría del Vehículo"
							placeholder="Selecciona una categoría"
							value={userData.categoria}
							onChange={(e) => handleSelectChange('categoria', e.target.value)}
							required
							className="bg-pink-100" // Color pastel para el select
						>
							{valoresSelecCategoria.map((categoria, index) => (
								<SelectItem className=' bg-pink-500' key={index} value={categoria}>
									{categoria}
								</SelectItem>
							))}
						</Select>
	
						<Checkbox className="text-pink-600"> {/* Color pastel para el checkbox */}
							Acepto recibir alertas y notificaciones
						</Checkbox>
	
						<Button type="submit" className="w-full bg-pink-500 hover:bg-pink-700 text-white"> {/* Botón pastel rosa */}
							Registrarse
						</Button>
					</form>
				</CardBody>
			</Card>
		</div>
	);
}
