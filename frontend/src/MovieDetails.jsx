import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Sidebar from './Sidebar';
import profile from './assets/profilepic.jpg';

const MovieDetails = () => {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [sidebarVisible, setSidebarVisible] = useState(false);
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`http://localhost:8080/movies/${id}`)
      .then((response) => setMovie(response.data))
      .catch((error) => console.error('Error fetching movie details:', error));

    const loggedInUser = localStorage.getItem('user');
    if (loggedInUser) {
      setUser(JSON.parse(loggedInUser));
    }
  }, [id]);

  const handleSignOut = () => {
    setUser(null);
    localStorage.removeItem('user');
    setSidebarVisible(false);
  };

  const handleBookTicket = () => {
    navigate(`/book/${id}`);
  };

  if (!movie) {
    return <div>Loading...</div>;
  }

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
          <h1
            className="text-xl font-bold cursor-pointer"
            onClick={() => navigate('/')}
          >
            The<span className="text-yellow-400">Popcorn</span>Bucket
          </h1>
        </div>
        <button
          className="text-white text-lg"
          onClick={() => setSidebarVisible(true)}
        >
          ☰
        </button>
      </header>

      {/* Main Content */}
      <main className="flex-grow container mx-auto mt-8 p-4">
        <div className="bg-white shadow-md rounded-lg overflow-hidden p-6">
          <img
            src={`/images/movies/${movie.imageName}`}
            alt={movie.movieName}
            className="w-full h-96 object-cover rounded-lg"
          />
          <div className="mt-6">
            <h2 className="text-3xl font-bold text-[#1e293b]">{movie.movieName}</h2>
            <p className="text-lg text-gray-600 mt-2">{movie.genre}</p>
            <p className="text-lg text-gray-600"> {movie.language}   {movie.ageRating}</p>
            <p className="text-lg text-gray-600"><strong>Rating:</strong> {movie.ratings}</p> 
             <p className="text-lg text-gray-600"><strong>Duration:</strong> {movie.duration}</p>
             <p className="text-lg text-gray-600 mt-4 font-bold">Description</p>
             <p className="text-lg text-gray-600">{movie.description}</p>
             <p className="text-lg text-gray-600 mt-4 font-bold">Cast</p>
            <p className="text-lg text-gray-600">{movie.cast}</p>
          </div>
        </div>
      </main>

      {/* Book Ticket Button */}
      <button
  onClick={handleBookTicket}
  className="fixed bottom-28 right-10 bg-red-600 text-white text-xl font-bold px-8 py-4 rounded-full shadow-lg hover:bg-red-700 transition duration-300"
>
  Book Ticket
</button>

      {/* Footer */}
      <footer className="w-full bg-[#1e293b] text-white text-center py-10 mt-40">
        <p>&copy; 2025 ThePopcornBucket. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default MovieDetails;
