import React from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

function Navbar({ user, cartCount, onLogout }) {
  const navigate = useNavigate();
  const location = useLocation();

  const isActive = (path) => {
    return location.pathname === path ? "active" : "";
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          🛍️ ElectroShop
        </Link>
        
        <div className="navbar-menu">
          <Link to="/" className={`navbar-link ${isActive("/")}`}>
            Products
          </Link>
          
          {user && (
            <>
              <Link to="/cart" className={`navbar-link ${isActive("/cart")}`}>
                Cart 
                {cartCount > 0 && <span className="cart-count">{cartCount}</span>}
              </Link>
              <Link to="/orders" className={`navbar-link ${isActive("/orders")}`}>
                My Orders
              </Link>
            </>
          )}

          <div className="navbar-user">
            {user ? (
              <>
                <span className="user-badge">👤 {user.name}</span>
                <button 
                  onClick={() => {
                    onLogout();
                    navigate("/login");
                  }} 
                  className="btn btn-secondary btn-sm"
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link to="/login" className="btn btn-secondary btn-sm">
                  Login
                </Link>
                <Link to="/register" className="btn btn-primary btn-sm">
                  Register
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
