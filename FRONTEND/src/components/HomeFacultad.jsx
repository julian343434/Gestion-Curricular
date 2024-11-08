const HomeFacultad = () => {
	return (
		<div className="flex justify-center items-center h-64 w-full"> {/* Adjust height as needed */}
			<img
				src="./image.png"
				className="object-contain h-full w-full max-h-56 max-w-md" // max-h sets a maximum height, max-w controls width limit
				alt="Facultad Image"
			/>
		</div>
	);
};
export default HomeFacultad;
