import { useNavigate } from 'react-router-dom';

const useLogout = () => {
    const navegate = useNavigate();

    const handleLogout = () => {
        // Remove the JWT token from local storage
        localStorage.removeItem('jwtToken');
        
        // Optionally, you can also clear any other stored user information
        // localStorage.removeItem('user');

        // Redirect the user to the login page
        navegate('/login');
    };

    return handleLogout;
};

export default useLogout;
