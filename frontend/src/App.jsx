// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import SignUpLoginForm from './LoginForm.jsx';
import Homepage from './Homepage.jsx';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/home" />} />
        <Route path="/home" element={<Homepage />} />
        <Route path="/login" element={<SignUpLoginForm />} />
       
      </Routes>
    </Router>
  );
};

export default App;
