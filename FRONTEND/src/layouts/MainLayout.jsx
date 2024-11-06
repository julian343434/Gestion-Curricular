const MainLayout = ({ children }) => {

    console.log("MainLayout");

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
            {children}
        </div>
    );
};

export default MainLayout;