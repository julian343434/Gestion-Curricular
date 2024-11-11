import React, { useState } from 'react';
import { SearchIcon } from 'lucide-react';

export default function PlanesDeEstudio() {
	const [searchQuery, setSearchQuery] = useState('');
	const [currentPage, setCurrentPage] = useState(1);
	const itemsPerPage = 6;

	const carreras = [
		{
			nombre: 'Ingeniería en Sistemas',
			creditos: 165,
			duracion: '5 años',
			codigo: 'SNIES: 4167',
			registro: 'Registro calificado: 20518 de 2017',
		},
		{
			nombre: 'Medicina Veterinaria y Zootecnia',
			creditos: 178,
			duracion: '5 años',
			codigo: 'SNIES: 1690',
			registro: 'Registro calificado: 12417',
		},
		{
			nombre: 'Enfermería',
			creditos: 162,
			duracion: '5 años',
			codigo: 'SNIES: 1693',
			registro: 'Registro calificado: 024696 de 2022',
			acreditacion: 'Acreditada en Alta Calidad: Res. MEN 007570 de 2023',
		},
	];

	const facultades = [
		{ nombre: 'Ciencias Económicas', carreras: 4 },
		{ nombre: 'Ciencias Agropecuarias y Recursos Naturales', carreras: 4 },
		{ nombre: 'Ciencias de la Salud', carreras: 4 },
		{ nombre: 'Ciencias Básicas e Ingeniería', carreras: 5 },
		{ nombre: 'Ciencias Humanas y de la Educación', carreras: 6 },
	];

	const filteredCarreras = carreras.filter((carrera) =>
		carrera.nombre.toLowerCase().includes(searchQuery.toLowerCase())
	);

	const indexOfLastItem = currentPage * itemsPerPage;
	const indexOfFirstItem = indexOfLastItem - itemsPerPage;
	const currentCarreras = filteredCarreras.slice(indexOfFirstItem, indexOfLastItem);

	const handleSearch = (e) => {
		setSearchQuery(e.target.value);
		setCurrentPage(1);
	};

	return (
		<div className="w-full min-h-screen bg-gray-50">
			<div className="w-full bg-[#C41E3A] text-white py-16 px-4 text-center">
				<h1 className="text-4xl md:text-5xl font-bold mb-4">Planes de Estudio</h1>
				<p className="text-xl md:text-2xl">
					Explora nuestras carreras y conoce la oferta académica
				</p>
			</div>
			<div> foto faculdad</div>
			<div className="container mx-auto px-4 py-8">
				<div className="flex flex-col md:flex-row items-center justify-center mb-8 gap-4">
					<div className="relative max-w-md w-full">
						<input
							type="text"
							placeholder="Buscar carrera..."
							value={searchQuery}
							onChange={handleSearch}
							className="w-full px-4 py-2 pl-10 pr-4 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
							aria-label="Buscar carrera"
						/>
						<SearchIcon className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
					</div>
					<button className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition duration-300">
						Buscar
					</button>
				</div>

				<section aria-labelledby="carreras-heading" className="mb-12">
					<h2
						id="carreras-heading"
						className="text-3xl font-bold mb-6  text-black text-left"
					>
						Carreras
					</h2>
					<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
						{currentCarreras.map((carrera, index) => (
							<div
								key={index}
								className="bg-white rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-300 overflow-hidden flex flex-col"
							>
								<div className="p-6 flex-1">
									<h3 className="text-2xl font-bold mb-4 text-black">
										{carrera.nombre}
									</h3>
									<ul className="space-y-2 text-black text-left">
										<li>
											<strong>Créditos:</strong> {carrera.creditos}
										</li>
										<li>
											<strong>Duración:</strong> {carrera.duracion}
										</li>
										<li>
											<strong>{carrera.codigo}</strong>
										</li>
										<li>{carrera.registro}</li>
										{carrera.acreditacion && (
											<li className="text-green-600">
												{carrera.acreditacion}
											</li>
										)}
									</ul>
								</div>
								<div className="px-6 pb-6 pt-0 bg-gray-50">
									<button className="w-full bg-[#C41E3A] text-white py-2 rounded-lg hover:bg-red-700 transition duration-300">
										Ver Plan de Estudios
									</button>
								</div>
							</div>
						))}
					</div>
					{filteredCarreras.length > itemsPerPage && (
						<div className="mt-8 flex justify-center">
							{/* Implementación básica de paginación */}
							{Array.from(
								{
									length: Math.ceil(
										filteredCarreras.length / itemsPerPage
									),
								},
								(_, i) => (
									<button
										key={i}
										onClick={() => setCurrentPage(i + 1)}
										className={`mx-1 px-3 py-1 rounded ${
											currentPage === i + 1
												? 'bg-blue-600 text-white'
												: 'bg-gray-200'
										}`}
									>
										{i + 1}
									</button>
								)
							)}
						</div>
					)}
				</section>

				<section aria-labelledby="facultades-heading">
					<h2
						id="facultades-heading"
						className="text-3xl font-bold mb-6 text-left text-black"
					>
						Nuestras Facultades
					</h2>
					<div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
						{facultades.map((facultad, index) => (
							<div
								key={index}
								className="bg-white rounded-lg shadow hover:shadow-md transition-shadow duration-300 p-4"
							>
								<h3 className="font-semibold text-lg mb-2 text-black">
									{facultad.nombre}
								</h3>
								<p className="text-sm text-gray-600">
									{facultad.carreras} carreras disponibles
								</p>
							</div>
						))}
					</div>
				</section>
			</div>
		</div>
	);
}
