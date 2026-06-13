import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";

import ProductList from "./pages/ProductList";
import Cart from "./pages/Cart";
import Orders from "./pages/Orders";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Navbar from "./components/Navbar";

// Set up global Axios request interceptor
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

function App() {
  const [user, setUser] = useState(null);
  const [cartCount, setCartCount] = useState(0);

  // Function to load cart count
  const loadCartCount = useCallback(async (userId) => {
    if (!userId) return;
    try {
      const res = await axios.get(`http://localhost:8080/api/cart/${userId}`);
      const totalQty = res.data.reduce((sum, item) => sum + item.quantity, 0);
      setCartCount(totalQty);
    } catch (error) {
      console.error("Failed to load cart count:", error);
    }
  }, []);

  // Check authentication on startup
  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    const storedUser = localStorage.getItem("user");

    if (storedToken && storedUser) {
      try {
        const parsedUser = JSON.parse(storedUser);
        setUser(parsedUser);
        loadCartCount(parsedUser.id);

        // Verify/Refresh user info in the background
        axios.get("http://localhost:8080/api/auth/me")
          .then((res) => {
            setUser(res.data);
            localStorage.setItem("user", JSON.stringify(res.data));
          })
          .catch((err) => {
            if (err.response && (err.response.status === 401 || err.response.status === 403)) {
              handleLogout();
            }
          });
      } catch (e) {
        handleLogout();
      }
    }
  }, [loadCartCount]);

  const handleLoginSuccess = (userData) => {
    setUser(userData);
    loadCartCount(userData.id);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
    setCartCount(0);
  };

  const handleCartUpdated = () => {
    if (user) {
      loadCartCount(user.id);
    }
  };

  return (
    <BrowserRouter>
      <Navbar user={user} cartCount={cartCount} onLogout={handleLogout} />
      <Routes>
        <Route 
          path="/" 
          element={<ProductList user={user} onCartUpdated={handleCartUpdated} />} 
        />
        <Route 
          path="/cart" 
          element={
            user ? (
              <Cart user={user} onCartUpdated={handleCartUpdated} />
            ) : (
              <Navigate to="/login" replace />
            )
          } 
        />
        <Route 
          path="/orders" 
          element={
            user ? (
              <Orders user={user} />
            ) : (
              <Navigate to="/login" replace />
            )
          } 
        />
        <Route 
          path="/login" 
          element={
            user ? (
              <Navigate to="/" replace />
            ) : (
              <Login onLoginSuccess={handleLoginSuccess} />
            )
          } 
        />
        <Route 
          path="/register" 
          element={
            user ? <Navigate to="/" replace /> : <Register />
          } 
        />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;