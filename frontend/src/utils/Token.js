// utils/token.js
export const setToken = (token) => {
    localStorage.setItem('jwtToken', token);
  };
  
  export const getToken = () => {
    return localStorage.getItem('jwtToken');
  };
  
  export const removeToken = () => {
    localStorage.removeItem('jwtToken');
  };

  export const isTokenPresent = () =>{
    const token = getToken();
    if(token) return true;
    else return false;
  }
  