import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Navigation, Pagination, Autoplay } from 'swiper/modules';
import profile from './assets/profilepic.jpg';
import Sidebar from './Sidebar.jsx';

const Homepage = () => {
  const [movies, setMovies] = useState([]);
  const [events, setEvents] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [user, setUser] = useState(null);
  const [sidebarVisible, setSidebarVisible] = useState(false);
  const navigate = useNavigate();

  const banners = [
    { image: '/images/ads/ad1.jpg', link: 'https://www.example.com/ad1' },
    { image: '/images/ads/ad2.jpg', link: 'https://www.example.com/ad2' },
    { image: '/images/ads/ad3.webp', link: 'https://www.example.com/ad3' },
    { image: '/images/ads/ad4.jpg', link: 'https://www.example.com/ad4' },
  ];

  useEffect(() => {
    axios
      .get('http://localhost:8080/movies')
      .then((response) => setMovies(response.data))
      .catch((error) => console.error('Error fetching movies:', error));

    axios
      .get('http://localhost:8080/events')
      .then((response) => setEvents(response.data))
      .catch((error) => console.error('Error fetching events:', error));

    const loggedInUser = localStorage.getItem('user');
    if (loggedInUser) {
      setUser(JSON.parse(loggedInUser));
    }
  }, []);

  const handleSignOut = () => {
    setUser(null);
    localStorage.removeItem('user');
    setSidebarVisible(false);
  };

  const filteredMovies = movies.filter((movie) =>
    (movie.movieName || "").toLowerCase().includes((searchTerm || "").toLowerCase())
  );

  const filteredEvents = events.filter((event) =>
    (event.event_name || "").toLowerCase().includes((searchTerm || "").toLowerCase())
  );

  return (
    <div className="w-full h-full bg-[#f4f4f4] flex flex-col">
      {/* Sidebar */}
      <Sidebar
        sidebarVisible={sidebarVisible}
        setSidebarVisible={setSidebarVisible}
        user={user}
        profile={profile}
        handleSignOut={handleSignOut}
      />
      {/* Header */}
      <header className="flex justify-between items-center bg-[#1e293b] text-white p-4 shadow-md">
        <div className="flex items-center space-x-4">
          <h1 className="text-xl font-bold">The<span className="text-yellow-400">Popcorn</span>Bucket</h1>
          <input
            type="text"
            placeholder="Search movies or events..."
            className="p-2 w-200 rounded font-semibold text-black border border-gray-300 bg-white"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <div className="flex items-center space-x-4">
          {user ? (
            <span className="text-white font-normal">Welcome, {user.username}!</span>
          ) : (
            <button
              className="border border-white text-white px-4 py-2 rounded-md hover:bg-white hover:text-[#1e293b] transition-colors"
              onClick={() => navigate('/login')}
            >
              Sign In
            </button>
          )}
          <img
            src={profile}
            alt="Profile"
            className="h-10 w-10 rounded-full border-2 border-white cursor-pointer"
            onClick={() => setSidebarVisible(!sidebarVisible)}
          />
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 overflow-y-auto">
        <section className="w-full max-w-[1200px] mx-auto mt-4">
          <Swiper
            modules={[Navigation, Pagination, Autoplay]}
            navigation
            pagination={{ clickable: true }}
            spaceBetween={10}
            slidesPerView={1}
            autoplay={{ delay: 5000, disableOnInteraction: false }}
            loop={true}
            className="rounded-lg overflow-hidden shadow-md"
          >
            {banners.map((banner, index) => (
              <SwiperSlide key={index}>
                <a href={banner.link} target="_blank" rel="noopener noreferrer">
                  <img
                    src={banner.image}
                    alt={`Advertisement ${index + 1}`}
                    className="w-full h-90 object-cover"
                  />
                </a>
              </SwiperSlide>
            ))}
          </Swiper>
        </section>

        <section className="mt-8 px-4">
          <h2 className="text-3xl font-semibold text-[#1e293b] mb-6">Movies</h2>
          <Swiper
            modules={[Navigation, Pagination]}
            navigation
            pagination={{ clickable: true }}
            spaceBetween={20}
            slidesPerView={3}
            breakpoints={{
              1024: { slidesPerView: 3 },
              768: { slidesPerView: 2 },
              640: { slidesPerView: 1 },
            }}
            className="px-8"
          >
            {filteredMovies.length > 0 ? (
              filteredMovies.map((movie) => (
                <SwiperSlide key={movie.movieId}>
                  <div className="bg-white shadow-md rounded-lg overflow-hidden hover:shadow-lg transition-shadow">
                    <img
                      src={`/images/movies/${movie.image_name}`}
                      alt={movie.movieName}
                      className="w-full h-48 object-cover"
                    />
                    <div className="p-4">
                      <h3 className="text-lg font-bold text-[#1e293b]">{movie.movieName}</h3>
                      <p className="text-sm text-gray-600">{movie.genre}</p>
                      <p className="text-sm text-gray-600">Rating: {movie.ratings}</p>
                    </div>
                  </div>
                </SwiperSlide>
              ))
            ) : (
              <p className="text-gray-500">No movies available</p>
            )}
          </Swiper>

          <h2 className="text-3xl font-semibold text-[#1e293b] mt-12 mb-6">Events</h2>
          <Swiper
            modules={[Navigation, Pagination]}
            navigation
            pagination={{ clickable: true }}
            spaceBetween={20}
            slidesPerView={3}
            breakpoints={{
              1024: { slidesPerView: 3 },
              768: { slidesPerView: 2 },
              640: { slidesPerView: 1 },
            }}
            className="px-8"
          >
            {filteredEvents.length > 0 ? (
              filteredEvents.map((event) => (
                <SwiperSlide key={event.event_ID}>
                  <div className="bg-white shadow-md rounded-lg overflow-hidden hover:shadow-lg transition-shadow">
                    <img
                      src={`/images/events/${event.image}`}
                      alt={event.event_name}
                      className="w-full h-48 object-cover"
                    />
                    <div className="p-4">
                      <h3 className="text-lg font-bold text-[#1e293b]">{event.event_name}</h3>
                      <p className="text-sm text-gray-600">{event.event_address}</p>
                      <p className="text-sm text-gray-600">Type: {event.event_type}</p>
                    </div>
                  </div>
                </SwiperSlide>
              ))
            ) : (
              <p className="text-gray-500">No events available</p>
            )}
          </Swiper>
        </section>

        <footer className="w-full bg-[#1e293b] text-white text-center py-10 mt-40">
          <p>&copy; 2025 ThePopcornBucket. All rights reserved.</p>
        </footer>
      </main>
    </div>
  );
};

export default Homepage;
