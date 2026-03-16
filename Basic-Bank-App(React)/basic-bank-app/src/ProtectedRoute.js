import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
  const isLoggedIn = useSelector(state => state.isLoggedIn);

  useEffect(() => {
    if (!isLoggedIn) {
      alert("⚠️ Access Denied: Please login to your VR BANK account first.");
    }
  }, [isLoggedIn]);

  if (!isLoggedIn) {
    // Redirect to login if the user is not authenticated
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;