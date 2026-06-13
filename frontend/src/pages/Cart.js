import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

function Cart({ user, onCartUpdated }) {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    loadCart();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user]);

  // Load Cart
  const loadCart = async () => {
    if (!user) return;
    try {
      const res = await axios.get(`http://localhost:8080/api/cart/${user.id}`);
      setCartItems(res.data);
    } catch (error) {
      console.error("Error loading cart:", error);
    } finally {
      setLoading(false);
    }
  };

  // Update Quantity
  const handleQuantityChange = async (item, delta) => {
    // If quantity is 1 and we want to decrement, remove the item
    if (item.quantity === 1 && delta === -1) {
      removeItem(item.id);
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/cart", {
        userId: user.id,
        productId: item.productId,
        quantity: delta,
      });
      loadCart();
      onCartUpdated();
    } catch (error) {
      console.error("Error updating quantity:", error);
      alert("Failed to update item quantity.");
    }
  };

  // Remove Item
  const removeItem = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/cart/${id}`);
      alert("✅ Item removed from cart");
      loadCart();
      onCartUpdated();
    } catch (error) {
      console.error("Error removing item:", error);
    }
  };

  // Place Order
  const placeOrder = async () => {
    try {
      const res = await axios.post(`http://localhost:8080/api/orders/place/${user.id}`, {});
      console.log("Order placed:", res.data);
      alert("🎉 Order Placed Successfully!");
      onCartUpdated();
      navigate("/orders");
    } catch (error) {
      console.error(error);
      alert("❌ Order Placement Failed");
    }
  };

  // Calculations
  const total = cartItems.reduce(
    (sum, item) => sum + (item.productPrice || 0) * item.quantity,
    0
  );

  if (loading) {
    return (
      <div className="container empty-state">
        <h3>Loading your shopping cart...</h3>
      </div>
    );
  }

  return (
    <div className="container">
      <h1 style={{ marginBottom: "32px", fontSize: "2.2rem" }}>🛒 Shopping Cart</h1>

      {cartItems.length === 0 ? (
        <div className="glass-panel empty-state">
          <div className="empty-icon">🛒</div>
          <h2>Your Cart is Empty</h2>
          <p style={{ color: "var(--text-secondary)", margin: "10px 0 24px 0" }}>
            Add some premium items to your cart and start shopping.
          </p>
          <Link to="/" className="btn btn-primary">
            Start Shopping
          </Link>
        </div>
      ) : (
        <div className="cart-layout">
          {/* Cart Items List */}
          <div className="cart-items">
            {cartItems.map((item) => (
              <div key={item.id} className="cart-item glass-panel">
                <img
                  src={item.productImageUrl || "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&q=80"}
                  alt={item.productName || "Product"}
                  className="cart-item-img"
                />
                
                <div className="cart-item-details">
                  <h3 className="cart-item-name">{item.productName || `Product ID: ${item.productId}`}</h3>
                  <p className="cart-item-price">
                    ₹{(item.productPrice || 0).toLocaleString("en-IN")} each
                  </p>
                  <p style={{ fontSize: "0.85rem", color: "var(--text-muted)", marginTop: "4px" }}>
                    Subtotal: ₹{((item.productPrice || 0) * item.quantity).toLocaleString("en-IN")}
                  </p>
                </div>

                <div style={{ display: "flex", alignItems: "center", gap: "20px" }}>
                  <div className="quantity-controls">
                    <button 
                      onClick={() => handleQuantityChange(item, -1)}
                      className="quantity-btn"
                    >
                      -
                    </button>
                    <span className="quantity-value">{item.quantity}</span>
                    <button 
                      onClick={() => handleQuantityChange(item, 1)}
                      className="quantity-btn"
                    >
                      +
                    </button>
                  </div>

                  <button
                    onClick={() => removeItem(item.id)}
                    className="btn btn-danger btn-sm"
                  >
                    Remove
                  </button>
                </div>
              </div>
            ))}
          </div>

          {/* Cart Summary */}
          <div className="cart-summary glass-panel">
            <h2 className="summary-title">Order Summary</h2>
            <div className="summary-row">
              <span>Items Total</span>
              <span>₹{total.toLocaleString("en-IN")}</span>
            </div>
            <div className="summary-row">
              <span>Delivery</span>
              <span style={{ color: "var(--accent-success)" }}>FREE</span>
            </div>
            
            <div className="summary-row summary-total">
              <span>Grand Total</span>
              <span>₹{total.toLocaleString("en-IN")}</span>
            </div>

            <button
              onClick={placeOrder}
              className="btn btn-primary"
              style={{ width: "100%", marginTop: "24px", padding: "14px" }}
            >
              Secure Checkout
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Cart;