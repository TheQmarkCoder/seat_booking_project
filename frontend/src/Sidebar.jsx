import React from 'react';
import { useNavigate } from 'react-router-dom';

const Sidebar = ({ sidebarVisible, setSidebarVisible, user, profile, handleSignOut }) => {
  const navigate = useNavigate();

  return (
    <div
      className={`fixed top-0 right-0 h-full w-64 bg-[#1e293b] text-white transform transition-transform duration-300 z-50 ${
        sidebarVisible ? 'translate-x-0' : 'translate-x-full'
      }`}
    >
      <div className="flex flex-col h-full p-4">
        <button
          className="text-white self-end text-lg"
          onClick={() => setSidebarVisible(false)}
        >
          &times;
        </button>
        <div className="flex flex-col items-center my-6">
          <img
            src={profile}
            alt="Profile"
            className="h-20 w-20 rounded-full border-2 border-white"
          />
          <h2 className="mt-4 text-lg font-semibold">{user ? user.username : 'Guest'}</h2>
        </div>
        <nav className="flex-1">
          <ul className="space-y-4">
            <li>
              <button
                onClick={() => {
                  navigate('/profile');
                  setSidebarVisible(false);
                }}
                className="w-full text-left text-white hover:underline"
              >
                Profile
              </button>
            </li>
            <li>
              <button
                onClick={() => {
                  navigate('/home');
                  setSidebarVisible(false);
                }}
                className="w-full text-left text-white hover:underline"
              >
                Home
              </button>
            </li>
            <li>
              <button
                onClick={() => {
                  navigate('/movies');
                  setSidebarVisible(false);
                }}
                className="w-full text-left text-white hover:underline"
              >
                Movies
              </button>
            </li>
            <li>
              <button
                onClick={() => {
                  navigate('/events');
                  setSidebarVisible(false);
                }}
                className="w-full text-left text-white hover:underline"
              >
                Events
              </button>
            </li>
            {user && (
              <li>
                <button
                  onClick={handleSignOut}
                  className="w-full text-left text-white hover:underline"
                >
                  Sign Out
                </button>
              </li>
            )}
          </ul>
        </nav>
      </div>
    </div>
  );
};

export default Sidebar;
