import { useNavigate } from 'react-router-dom';
import { removeToken } from '../utils/Token';

const useLogout = () => {
    const navegate = useNavigate();

    const handleLogout = () => {
        removeToken();
        navegate('/login');
    };

    return handleLogout;
};

export default useLogout;
