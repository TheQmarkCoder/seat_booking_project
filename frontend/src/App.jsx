// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import SignUpLoginForm from './LoginForm.jsx';
import Homepage from './Homepage.jsx';
import Movies from './Movies.jsx';
import EventsPage from './Events.jsx';
import ProfilePage from './Profile.jsx';

const storedUser = JSON.parse(localStorage.getItem("user")); // Parse the stored user object
const user_ID = storedUser?.user_ID || null; // Safely extract user_ID


const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/home" />} />
        <Route path="/home" element={<Homepage />} />
        <Route path="/movies" element={<Movies />} />
        <Route path="/events" element={<EventsPage />} />
        <Route path="/profile" element={<ProfilePage userId={user_ID} />} />
        <Route path="/login" element={<SignUpLoginForm />} />
       
      </Routes>
    </Router>
  );
};

export default App;
