import React, { createContext, useState } from 'react';

export const GestionPlanEstudioContext = createContext();

export const GestionPlanEstudioProvider = ({ children }) => {
    const [refreshTrigger, setRefreshTrigger] = useState(0);
    const [isFormSubmitted, setIsFormSubmitted] = useState(false); // Nuevo estado
    const [isDialogOpen, setIsDialogOpen] = useState(false);


    return (
        <GestionPlanEstudioContext.Provider value={{isDialogOpen, setIsDialogOpen   , isFormSubmitted, setIsFormSubmitted}}>
            {children}
        </GestionPlanEstudioContext.Provider>
    );
};


