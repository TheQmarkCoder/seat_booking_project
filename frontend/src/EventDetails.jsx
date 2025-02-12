import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import Sidebar from "./Sidebar";
import profile from "./assets/profilepic.jpg";

const EventDetails = () => {
  const { id } = useParams();
  const [event, setEvent] = useState(null);
  const [sidebarVisible, setSidebarVisible] = useState(false);
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`http://localhost:8080/events/${id}`)
      .then((response) => setEvent(response.data))
      .catch((error) => console.error("Error fetching event details:", error));

    const loggedInUser = localStorage.getItem("user");
    if (loggedInUser) {
      setUser(JSON.parse(loggedInUser));
    }
  }, [id]);

  const handleSignOut = () => {
    setUser(null);
    localStorage.removeItem("user");
    setSidebarVisible(false);
  };

  const handleBookTicket = () => {
    navigate(`/book/${id}`);
  };

  if (!event) {
    return <div className="text-center mt-10 text-xl">Loading...</div>;
  }

  return (
    <div className="min-h-screen flex flex-col bg-[#f4f4f4]">
      <Sidebar
        sidebarVisible={sidebarVisible}
        setSidebarVisible={setSidebarVisible}
        user={user}
        profile={profile}
        handleSignOut={handleSignOut}
      />

      <header className="w-full flex justify-between items-center bg-[#1e293b] text-white p-4 shadow-md">
        <h1
          className="text-xl font-bold cursor-pointer"
          onClick={() => navigate("/")}
        >
          The<span className="text-yellow-400">Popcorn</span>Bucket
        </h1>
        <button className="text-white text-lg" onClick={() => setSidebarVisible(true)}>
          ☰
        </button>
      </header>

      <main className="flex-grow container mx-auto mt-8 p-4">
        <div className="bg-white shadow-md rounded-lg overflow-hidden p-6">
          <img
            src={`/images/events/${event.image}`}
            alt={event.event_name}
            className="w-full h-96 object-cover rounded-lg"
          />
          
          <h2 className="text-3xl font-bold text-[#1e293b] mt-6">{event.event_name}</h2>
          <p className="text-lg text-gray-600">{event.description}</p>

          <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-4">
            <p className="text-lg text-gray-600"><strong>Type:</strong> {event.event_type}</p>
            <p className="text-lg text-gray-600"><strong>Language:</strong> {event.language}</p>
            <p className="text-lg text-gray-600"><strong>Rating:</strong> {event.rating}</p>
            <p className="text-lg text-gray-600"><strong>Age Restriction:</strong> {event.age_restriction ? "Yes" : "No"}</p>
            <p className="text-lg text-gray-600"><strong>Artists:</strong> {event.event_artists}</p>
            <p className="text-lg text-gray-600"><strong>Address:</strong> {event.event_address}</p>
            <p className="text-lg text-gray-600"><strong>Indoors:</strong> {event.is_indoors ? "Yes" : "No"}</p>
            <p className="text-lg text-gray-600"><strong>Duration:</strong> {event.event_duration}</p>
            <p className="text-lg text-gray-600"><strong>Date & Time:</strong> {new Date(event.date).toLocaleString()}</p>
            <p className="text-lg text-gray-600"><strong>Available Seats:</strong> {event.available_seats} / {event.total_seats}</p>
            <p className="text-lg text-gray-600"><strong>Reserved Seats:</strong> {event.reserved_seats}</p>
            <p className="text-lg text-gray-600"><strong>Disability Features:</strong> {event.disability_features}</p>
          </div>
        </div>
      </main>

      <button
        onClick={handleBookTicket}
        className="fixed bottom-28 right-10 bg-red-600 text-white text-xl font-bold px-8 py-4 rounded-full shadow-lg hover:bg-red-700 transition duration-300"
      >
        Book Ticket
      </button>

      <footer className="w-full bg-[#1e293b] text-white text-center py-10 mt-40">
        <p>&copy; 2025 ThePopcornBucket. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default EventDetails;
