import React, { useState, useEffect } from 'react';
import { Car } from 'lucide-react';

const alertTypes = {
	"Cierres y viales": { icon: Car, color: 'bg-pomegranate' },
	"Pico y placa": { icon: Car, color: 'bg-curious-blue' },
	"Semaforos Dañados": { icon: Car, color: 'bg-silver' },
	"Accidentes": { icon: Car, color: 'bg-elm' },
	"Trancones": { icon: Car, color: 'bg-jelly-bean' },
	"Baches": { icon: Car, color: 'bg-scooter' },
	"Cambios sentido": { icon: Car, color: 'bg-shakespeare' },
};

const AlertCard = ({ alert }) => {
	const AlertIcon = alertTypes[alert.tipo_alerta]?.icon || Car; // Usar icono por defecto
	return (
		<div
			className={`${
				alertTypes[alert.tipo_alerta]?.color || 'bg-gray-500'
			} text-white p-4 rounded-lg shadow-lg mb-4 flex items-center justify-between`}
		>
			<div className="flex items-center space-x-4">
				<div className="bg-white p-2 rounded-full">
					<AlertIcon
						className={`w-6 h-6 ${
							alertTypes[alert.tipo_alerta]?.color.replace('bg-', 'text-') ||
							'text-gray-500'
						}`}
					/>
				</div>
				<div>
					<h3 className="font-bold text-lg">{alert.tipo_alerta}</h3>
					<p className="text-sm">{alert.descripcion}</p>
				</div>
			</div>
			<div className="text-right">
				<p className="text-xs font-bold">{new Date(alert.fecha_hora).toLocaleString()}</p>
				<span
					className={`text-xs px-2 py-1 rounded ${
						alert.urgency === 'URGENT'
							? 'bg-red-700'
							: alert.urgency === 'WARNING'
							? 'bg-yellow-700'
							: 'bg-green-700'
					}`}
				>
					{alert.urgency}
				</span>
			</div>
		</div>
	);
};

export default function AlertNotifications() {
	const [alerts, setAlerts] = useState([]);
	const [activeTab, setActiveTab] = useState('Alerts');

	useEffect(() => {
		const fetchAlerts = async () => {
			try {
				const response = await fetch('http://localhost:3001/api/alertas');
				if (!response.ok) {
					throw new Error(`HTTP error! status: ${response.status}`);
				}
				const data = await response.json();
				console.log(data); // Log de los datos
				setAlerts(data);
			} catch (error) {
				console.error('Error fetching alerts:', error);
			}
		};

		// Llamar a fetchAlerts inmediatamente
		fetchAlerts();

		// Establecer un intervalo para llamar a fetchAlerts cada 10 segundos (por ejemplo)
		const intervalId = setInterval(fetchAlerts, 10000);

		// Limpiar el intervalo al desmontar el componente
		return () => clearInterval(intervalId);
	}, []);

	return (
		<div className="bg-gray-900 text-white p-6 rounded-lg shadow-xl mx-auto ">
			<h1 className="text-2xl font-bold mb-4"> Notificaciones</h1>
			<div className="flex space-x-2 mb-4 overflow-x-auto">
				{['Alertas'].map(
					(tab, index) => (
						<button
							key={index}
							className={`px-4 py-2 rounded-full ${
								activeTab === tab
									? 'bg-red-500'
									: 'bg-gray-700 hover:bg-gray-600'
							} transition-colors duration-200`}
							onClick={() => setActiveTab(tab)}
						>
							{tab}
						</button>
					)
				)}
			</div>
			<div className="space-y-4">
				{alerts.length > 0 ? (
					alerts.map((alert) => (
						<AlertCard key={alert.id_alerta} alert={alert} />
					))
				) : (
					<p>No alerts available</p>
				)}
			</div>
		</div>
	);
}
