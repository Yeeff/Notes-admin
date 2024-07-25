
import React, { useState } from 'react';
import {login} from '../services/AuthApi'
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
    const navegate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    const [isLogin, setIsLogin] = useState(true);

    const handleLogin = async (event) => {
        event.preventDefault();
        try {
            const response = await login( {
                username: username,
                password: password
            });
            const token = response.data.jwt;
            localStorage.setItem('jwtToken', token);

            navegate("/");

        } catch (error) {
            setError('Invalid username or password');
        }
    };

    const handleSignUp = async (event) => {
        event.preventDefault();
        try {
            //await axios.post('http://localhost:8080/api/auth/signup', {
            //    username,
            //    password
            //});
            setSuccessMessage('User created successfully! You can now log in.');
            setUsername('');
            setPassword('');
            setError('');
            setIsLogin(true);
        } catch (error) {
            setError('Error creating user. Please try again.');
        }
    };

    const toggleMode = () => {
        setIsLogin(!isLogin);
        setError('');
        setSuccessMessage('');
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card mb-4">
                        <div className="card-header">
                            <h2>{isLogin ? 'Login' : 'Sign Up'}</h2>
                        </div>
                        <div className="card-body">
                            <form onSubmit={isLogin ? handleLogin : handleSignUp}>
                                <div className="mb-3">
                                    <label htmlFor="username" className="form-label">Username:</label>
                                    <input
                                        type="text"
                                        id="username"
                                        className="form-control"
                                        value={username}
                                        onChange={(e) => setUsername(e.target.value)}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="password" className="form-label">Password:</label>
                                    <input
                                        type="password"
                                        id="password"
                                        className="form-control"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                {error && <div className="alert alert-danger">{error}</div>}
                                {successMessage && <div className="alert alert-success">{successMessage}</div>}
                                <button type="submit" className="btn btn-primary">{isLogin ? 'Login' : 'Sign Up'}</button>
                            </form>
                            <button className="btn btn-link mt-3" onClick={toggleMode}>
                                {isLogin ? 'Sign Up' : 'Cancel'}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
