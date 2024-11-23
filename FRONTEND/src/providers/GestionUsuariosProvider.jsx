import React, { createContext, useState } from 'react';

export const GestionUsuariosContext = createContext();

export const GestionUsuariosProvider = ({ children }) => {
    const [refreshTrigger, setRefreshTrigger] = useState(0);
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [createOpen , setCreatOpen] = useState(false);
    const [actualizarOpen , setActualizarOpen] = useState(false);
    

    return (
        <GestionUsuariosContext.Provider value={{createOpen , setCreatOpen ,  refreshTrigger, setRefreshTrigger , isDialogOpen, setIsDialogOpen , actualizarOpen , setActualizarOpen}}>
            {children}
        </GestionUsuariosContext.Provider>
    );
};


