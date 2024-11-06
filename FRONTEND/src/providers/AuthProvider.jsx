import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [userRole, setUserRole] = useState(localStorage.getItem('role'));

    return (
        <AuthContext.Provider value={{ token, setToken, userRole, setUserRole }}>
            {children}
        </AuthContext.Provider>
    );
};
