import React, { useState, useEffect } from "react";
import axios from "axios";
import Sidebar from "./Sidebar";
import profile from "./assets/profilepic.jpg";

const Profile = ({ userId }) => {
  const [user, setUser] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [sidebarVisible, setSidebarVisible] = useState(false);
  const [updatedDetails, setUpdatedDetails] = useState({});

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users/${userId}`);
        const userData = response.data;

        // Exclude user ID from editable fields
        const { id, ...editableData } = userData;

        setUser(userData);
        setUpdatedDetails(editableData);
      } catch (error) {
        console.error("Error fetching user details:", error);
      }
    };

    fetchUserDetails();
  }, [userId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedDetails((prevDetails) => ({ ...prevDetails, [name]: value }));
  };

  const handleSaveChanges = async () => {
    try {
      await axios.put(`http://localhost:8080/users/${userId}`, updatedDetails);
      alert("Profile updated successfully");
      alert("Sign out and login again to see changes")
      setUser((prevUser) => ({ ...prevUser, ...updatedDetails }));
      setIsEditing(false);
    } catch (error) {
      console.error("Error updating profile:", error);
      alert("Failed to update profile");
    }
  };

  const handleSignOut = () => {
    console.log("Sign out clicked");
    setUser(null);
    localStorage.removeItem("user");
    setSidebarVisible(false);
  };

  return (
    <div className="flex">
      <Sidebar
        sidebarVisible={sidebarVisible}
        setSidebarVisible={setSidebarVisible}
        user={user}
        profile={profile}
        handleSignOut={handleSignOut}
      />

      <div className="flex-grow p-4">
      <header className="fixed top-0 left-0 w-full flex justify-between items-center bg-[#1e293b] text-white p-4 shadow-md">
  <div className="flex items-center space-x-4">
    <h1
      className="text-xl font-bold cursor-pointer"
      onClick={() => window.location.href = '/'} // Navigate to the homepage
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


        <main className="mt-20 bg-white shadow-md rounded p-6 max-w-2xl mx-auto">
          <h2 className="text-2xl font-bold mb-4">Profile</h2>
          {user ? (
            <div className="space-y-4">
              
              {Object.keys(updatedDetails)
                .filter((field) => field !== "user_ID") // Exclude 'id' field
                .map((field) => (
                <div key={field}>
                  <label className="block font-semibold capitalize">
                    {field.replace(/_/g, " ")}
                  </label>
                  {isEditing ? (
                    <input
                      type="text"
                      name={field}
                      className="w-full p-2 border rounded"
                      value={updatedDetails[field] || ""}
                      onChange={handleInputChange}
                    />
                  ) : (
                    <p>{user[field]}</p>
                  )}
                </div>
              ))}
              <div className="flex space-x-4">
                {isEditing ? (
                  <>
                    <button
                      className="px-4 py-2 bg-blue-500 text-white rounded"
                      onClick={handleSaveChanges}
                    >
                      Save Changes
                    </button>
                    <button
                      className="px-4 py-2 bg-gray-300 rounded"
                      onClick={() => {
                        setUpdatedDetails(
                          Object.keys(user).reduce((details, key) => {
                            if (key !== "id") details[key] = user[key];
                            return details;
                          }, {})
                        );
                        setIsEditing(false);
                      }}
                    >
                      Cancel
                    </button>
                  </>
                ) : (
                  <button
                    className="px-4 py-2 bg-yellow-500 text-white rounded"
                    onClick={() => setIsEditing(true)}
                  >
                    Edit Profile
                  </button>
                )}
              </div>
            </div>
          ) : (
            <p>Loading...</p>
          )}
        </main>
      </div>
    </div>
  );
};

export default Profile;
