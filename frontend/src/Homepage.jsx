import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import profile from './assets/profilepic.jpg';

const Homepage = () => {
  const [movies, setMovies] = useState([]);
  const [events, setEvents] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [user, setUser] = useState(null); // Track user info after login
  const navigate = useNavigate();

  useEffect(() => {
    // Fetching movies and events from the backend
    axios.get('http://localhost:8080/movies')
      .then(response => setMovies(response.data))
      .catch(error => console.error('Error fetching movies:', error));

    axios.get('http://localhost:8080/events')
      .then(response => setEvents(response.data))
      .catch(error => console.error('Error fetching events:', error));

    // Simulate fetching user info if logged in (replace with actual API call)
    const loggedInUser = localStorage.getItem('user'); // Retrieve user from storage
    if (loggedInUser) {
      setUser(JSON.parse(loggedInUser));
    }
  }, []);

  const handleSignOut = () => {
    setUser(null);
    localStorage.removeItem('user'); // Clear user from storage
  };

  // Filter movies based on searchTerm
  const filteredMovies = movies.filter(movie =>
    (movie.movieName || "").toLowerCase().includes((searchTerm || "").toLowerCase())
  );

  // Filter events based on searchTerm
  const filteredEvents = events.filter(event =>
    (event.event_name || "").toLowerCase().includes((searchTerm || "").toLowerCase())
  );

  return (
    <div className="container mx-auto p-4 bg-[#d9d9d9] min-h-screen">
      <header className="flex justify-between items-center bg-[#3c6e71] text-white p-4 rounded-md shadow-md">
        <input
          type="text"
          placeholder="Search movies or events..."
          className="p-2 rounded w-1/2 font-serif text-black border border-white-300 bg-neutral-50"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <div className="flex items-center space-x-4">
          {user ? (
            <div className="flex items-center space-x-2">
              <span className="text-white font-medium">Welcome, {user.username}!</span>
              <button
                className="bg-[#ff6b6b] text-white px-4 py-2 rounded-md hover:bg-[#ff3b3b] transition-colors"
                onClick={handleSignOut}
              >
                Sign Out
              </button>
            </div>
          ) : (
            <button
              className="bg-[#ff6b6b] text-white px-4 py-2 rounded-md hover:bg-[#ff3b3b] transition-colors"
              onClick={() => navigate('/login')}
            >
              Sign In
            </button>
          )}
          <img src={profile} alt="Profile" className="h-10 w-10 rounded-full border-2 border-white" />
        </div>
      </header>

      <section className="mt-8">
        <h2 className="text-2xl font-serif text-[#284b63] mb-4">Movies</h2>
        <div className="grid grid-cols-3 gap-4">
          {filteredMovies.length > 0 ? (
            filteredMovies.map(movie => (
              <div
                className="bg-white shadow-md p-4 rounded-lg border-l-4 border-[#353535] hover:shadow-lg transition-shadow"
                key={movie.movieId}
              >
                <h3 className="text-lg font-medium text-[#073b4c]">{movie.movieName}</h3>
                <p className="text-sm text-gray-600">Genre: {movie.genre}</p>
                <p className="text-sm text-gray-600">Duration: {movie.duration}</p>
                <p className="text-sm text-gray-600">Language: {movie.language}</p>
                <p className="text-sm text-gray-600">Ratings: {movie.ratings}</p>
              </div>
            ))
          ) : (
            <p className="text-gray-500">No movies available</p>
          )}
        </div>

        <h2 className="text-2xl font-serif text-[#284b63] mt-8 mb-4">Events</h2>
        <div className="grid grid-cols-3 gap-4">
          {filteredEvents.length > 0 ? (
            filteredEvents.map(event => (
              <div
                className="bg-white shadow-md p-4 rounded-lg border-l-4 border-[#353535] hover:shadow-lg transition-shadow"
                key={event.event_ID}
              >
                <h3 className="text-lg font-medium text-[#073b4c]">{event.event_name}</h3>
                <p className="text-sm text-gray-600">Address: {event.event_address}</p>
                <p className="text-sm text-gray-600">Type: {event.event_type}</p>
              </div>
            ))
          ) : (
            <p className="text-gray-500">No events available</p>
          )}
        </div>
      </section>
    </div>
  );
};

export default Homepage;
