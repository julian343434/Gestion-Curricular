import React, { createContext, useState ,useEffect } from 'react';



export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [userRole, setUserRole] = useState(localStorage.getItem('role'));
    const [session , setSession] = useState(false);

    
    useEffect(() => {
        console.log("el valor de session es " , session);
      }, [session]);


    return (
        <AuthContext.Provider value={{ token, setToken, userRole, setUserRole , session , setSession}}>
            {children}
        </AuthContext.Provider>
    );
};
