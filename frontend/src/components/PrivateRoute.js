import React from 'react';
import { Navigate } from 'react-router-dom';
import {getToken, setToken, removeToken, isTokenPresent} from '../utils/Token'
import {jwtDecode} from "jwt-decode";

const PrivateRoute = ({ children, allowedRoles }) => {


    let decodedToken;

    if(isTokenPresent()) decodedToken = jwtDecode(getToken());
    else return <Navigate to="/login" />

    const user = {userName: decodedToken.sub, roles: decodedToken.authorities};

    var anyRoleMatch = false;

    if (!user) {
        return <Navigate to="/login" />;
    } else {
        let stringRoles = user.roles.split(",");
        stringRoles.forEach(userRole => {
            if (allowedRoles.includes(userRole)) anyRoleMatch = true;
        });
    }

    if (allowedRoles && !anyRoleMatch) {

        return <Navigate to="/unauthorized" />;
    }

    return children;
};

export default PrivateRoute;