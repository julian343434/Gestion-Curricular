import React, { useState , useContext } from 'react';
import PlanEstudioCarga from './PlanEstudioCarga';
import PlanEstudioActual from './PlanEstudioActual';
import PlanEstudioHistorial from './PlanEstudioHistorial';
import { GestionPlanEstudioContext } from "../providers/GestionPlanEstudioProvider";


export default function StudyPlanManager() {
	const [currentPlan, setCurrentPlan] = useState({
		name: 'Plan 2024',
		description: 'Plan detallado para el año académico 2024',
		year: 2024,
		file: 'plan2024.pdf',
	});

	const [planHistory, setPlanHistory] = useState([
		{ name: 'Plan 2023', year: 2023, file: 'plan2023.pdf' },
		{ name: 'Plan 2022', year: 2022, file: 'plan2022.pdf' },
	]);

	const [isFormSubmitted, setIsFormSubmitted] = useState(false); // Nuevo estado
  const { isDialogOpen} = useContext(GestionPlanEstudioContext);
  


	const handleModify = (plan) => {
		alert(`Modificar el plan: ${plan.name}`);
		// Lógica para modificar el plan.
		setCurrentPlan(plan);
	};

	const handleNewPlan = (newPlan) => {
		setPlanHistory([currentPlan, ...planHistory]); // Agregar el plan actual al historial
		setCurrentPlan(newPlan); // Actualizar el plan actual
		setIsFormSubmitted(true); // Cuando se envía el plan, cambiamos el estado
	};

	return (
		
			<div className="container  items-center justify-center mx-auto px-4 py-8 space-y-8">
				{/* Sección de carga de planes */}
				<PlanEstudioCarga onUpload={handleNewPlan} />

				{/* Solo renderizar los demás componentes si el formulario fue enviado */}
				{!isDialogOpen && ( 
					<>
						{/* Plan actual */}
						<PlanEstudioActual currentPlan={currentPlan} onModify={handleModify} />

						{/* Historial de planes */}
						<PlanEstudioHistorial planHistory={planHistory} onModify={handleModify} />
					</>
				)}
			</div>
		
	);
}
