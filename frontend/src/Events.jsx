import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Sidebar from './Sidebar';
import profile from './assets/profilepic.jpg';

const Events = () => {
  const [events, setEvents] = useState([]);
  const [filteredEvents, setFilteredEvents] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [sidebarVisible, setSidebarVisible] = useState(false);
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get('http://localhost:8080/events')
      .then((response) => setEvents(response.data))
      .catch((error) => console.error('Error fetching events:', error));

    // Load user from localStorage
    const loggedInUser = localStorage.getItem('user');
    if (loggedInUser) {
      setUser(JSON.parse(loggedInUser));
    }
  }, []);

  useEffect(() => {
    setFilteredEvents(
      events.filter((event) =>
        event.event_name?.toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
  }, [searchTerm, events]);

  const handleSignOut = () => {
    setUser(null);
    localStorage.removeItem('user');
    setSidebarVisible(false);
  };

  return (
    <div className="min-h-screen flex flex-col bg-[#f4f4f4]">
      {/* Sidebar */}
      <Sidebar
        sidebarVisible={sidebarVisible}
        setSidebarVisible={setSidebarVisible}
        user={user}
        profile={profile}
        handleSignOut={handleSignOut}
      />

      {/* Header */}
      <header className="w-full flex justify-between items-center bg-[#1e293b] text-white p-4 shadow-md">
        <div className="flex items-center space-x-4">
          <h1 className="text-xl font-bold cursor-pointer" onClick={() => navigate('/')}>
            The<span className="text-yellow-400">Popcorn</span>Bucket
          </h1>
          <input
            type="text"
            placeholder="Search events..."
            className="p-2 w-full sm:w-64 rounded font-semibold text-black border border-gray-300 bg-white"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <button className="text-white text-lg" onClick={() => setSidebarVisible(true)}>☰</button>
      </header>

      {/* Main Content */}
      <main className="flex-grow container mx-auto mt-8 p-4">
        <h2 className="text-3xl font-bold text-[#1e293b] mb-6">Events</h2>
        {filteredEvents.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredEvents.map((event) => (
              <div
                key={event.event_id}
                className="bg-white shadow-md rounded-lg overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
                onClick={() => navigate(`/event/${event.eventID}`)}
              >
                <img
                  src={event.image ? `/images/events/${event.image}` : '/assets/default-event.jpg'}
                  alt={event.event_name}
                  className="w-full h-48 object-cover"
                />
                <div className="p-4">
                  <h3 className="text-lg font-bold text-[#1e293b]">{event.event_name}</h3>
                  <p className="text-sm text-gray-600">{event.even_type}</p>
                  <p className="text-sm text-gray-600">Location: {event.event_address}</p>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-center text-gray-500">No events found.</p>
        )}
      </main>

      {/* Footer */}
      <footer className="w-full bg-[#1e293b] text-white text-center py-10 mt-40">
        <p>&copy; 2025 ThePopcornBucket. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default Events;
