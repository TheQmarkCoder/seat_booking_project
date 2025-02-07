import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './LoginForm.css';

const SignUpLoginForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    location: '',
    phone_number: '',
    role: ''
  });

  const [loginData, setLoginData] = useState({
    email: '',
    password: ''
  });

  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [isLogin, setIsLogin] = useState(false);
  const [loginError, setLoginError] = useState('');

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleLoginChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErrorMessage('');

    try {
      const response = await axios.post('http://localhost:8080/users', formData);
      if (response.status === 201) {
        alert('User added successfully!');
        
        // Store user data in localStorage
        localStorage.setItem('user', JSON.stringify({ 
          username: formData.username, 
          email: formData.email 
        }));

        setFormData({
          username: '',
          email: '',
          password: '',
          location: '',
          phone_number: '',
          role: ''
        });

        navigate('/home'); // Redirect to Homepage
      }
    } catch (error) {
      console.error('Error during form submission:', error);
      setErrorMessage('An error occurred. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setLoginError('');

    try {
      const response = await axios.post('http://localhost:8080/users/login', loginData);
      if (response.status === 200) {
        alert('Login successful!');
        
        // Store user data in localStorage
        const { username, email } = response.data; // Assuming API returns username and email
        localStorage.setItem('user', JSON.stringify({ username }));

        navigate('/home'); // Redirect to Homepage
      }
    } catch (error) {
      console.error('Error during login:', error);
      setLoginError('Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="split-screen">
      <div className="left-panel">
        <h1>Welcome Back!</h1>
        <p>To keep connected with us, please login with your personal info.</p>
        <button className="switch-button" onClick={() => setIsLogin(true)}>Login</button>
      </div>
      <div className="right-panel">
        <h1>Create Account</h1>
        <p>Enter your details to get started.</p>
        <form onSubmit={isLogin ? handleLogin : handleSubmit} className="auth-form">
          {!isLogin && (
            <>
              <input
                type="text"
                name="username"
                value={formData.username}
                onChange={handleChange}
                placeholder="Enter your name"
                required
                className="input-field"
              />
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Enter your email"
                required
                className="input-field"
              />
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Enter your password"
                required
                className="input-field"
              />
              <input
                type="text"
                name="location"
                value={formData.location}
                onChange={handleChange}
                placeholder="Enter your location"
                required
                className="input-field"
              />
              <input
                type="text"
                name="phone_number"
                value={formData.phone_number}
                onChange={handleChange}
                placeholder="Enter your phone number"
                required
                className="input-field"
              />
              <select
                name="role"
                value={formData.role}
                onChange={handleChange}
                required
                className="input-field"
              >
                <option value="">Select your role</option>
                <option value="customer">Customer</option>
                <option value="admin">Admin</option>
                <option value="organiser">Organiser</option>
              </select>
            </>
          )}
          {isLogin && (
            <>
              <input
                type="email"
                name="email"
                value={loginData.email}
                onChange={handleLoginChange}
                placeholder="Enter your email"
                required
                className="input-field"
              />
              <input
                type="password"
                name="password"
                value={loginData.password}
                onChange={handleLoginChange}
                placeholder="Enter your password"
                required
                className="input-field"
              />
            </>
          )}
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? (isLogin ? 'Logging in...' : 'Submitting...') : (isLogin ? 'Login' : 'Sign Up')}
          </button>
          {errorMessage && <div className="error-message">{errorMessage}</div>}
          {loginError && <div className="error-message">{loginError}</div>}
        </form>
        <button className="switch-button" onClick={() => setIsLogin(!isLogin)}>
          {isLogin ? 'Need an account? Sign Up' : 'Already have an account? Login'}
        </button>
      </div>
    </div>
  );
};

export default SignUpLoginForm;
