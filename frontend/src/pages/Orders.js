import React, { useEffect, useState } from "react";
import axios from "axios";

function Orders({ user }) {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [expandedOrders, setExpandedOrders] = useState({});
  const [orderItemsCache, setOrderItemsCache] = useState({});

  useEffect(() => {
    fetchOrders();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user]);

  const fetchOrders = async () => {
    if (!user) return;
    try {
      const res = await axios.get(`http://localhost:8080/api/orders/user/${user.id}`);
      // Sort orders by ID desc so newest are on top
      const sorted = res.data.sort((a, b) => b.id - a.id);
      setOrders(sorted);
    } catch (error) {
      console.error("Failed to load orders:", error);
    } finally {
      setLoading(false);
    }
  };

  const toggleOrderDetails = async (orderId) => {
    if (expandedOrders[orderId]) {
      setExpandedOrders((prev) => ({ ...prev, [orderId]: false }));
      return;
    }

    setExpandedOrders((prev) => ({ ...prev, [orderId]: true }));

    // Fetch items if not already cached
    if (!orderItemsCache[orderId]) {
      try {
        const res = await axios.get(`http://localhost:8080/api/orders/${orderId}/items`);
        setOrderItemsCache((prev) => ({ ...prev, [orderId]: res.data }));
      } catch (error) {
        console.error("Failed to load order items:", error);
      }
    }
  };

  const cancelOrder = async (orderId) => {
    const confirmCancel = window.confirm("⚠️ Are you sure you want to cancel this order?");
    if (!confirmCancel) return;

    try {
      await axios.put(`http://localhost:8080/api/orders/${orderId}/cancel`);
      alert("✅ Order Cancelled Successfully");
      fetchOrders();
    } catch (error) {
      console.error(error);
      alert("❌ Failed to cancel order. Orders can only be cancelled if they are in PLACED status.");
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return "N/A";
    const date = new Date(dateString);
    return date.toLocaleDateString("en-IN", {
      year: "numeric",
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  if (loading) {
    return (
      <div className="container empty-state">
        <h3>Loading order logs...</h3>
      </div>
    );
  }

  return (
    <div className="container">
      <h1 style={{ marginBottom: "32px", fontSize: "2.2rem" }}>📦 My Orders</h1>

      {orders.length === 0 ? (
        <div className="glass-panel empty-state">
          <div className="empty-icon">📦</div>
          <h2>No Orders Found</h2>
          <p style={{ color: "var(--text-secondary)", margin: "10px 0 24px 0" }}>
            You haven't placed any orders yet.
          </p>
        </div>
      ) : (
        <div className="orders-list">
          {orders.map((order) => {
            const isExpanded = expandedOrders[order.id];
            const items = orderItemsCache[order.id] || [];

            return (
              <div key={order.id} className="order-group glass-panel">
                {/* Order Header Summary */}
                <div className="order-header">
                  <div className="order-meta-info">
                    <div className="meta-item">
                      <span className="meta-label">Order ID</span>
                      <span className="meta-val">#{order.id}</span>
                    </div>
                    <div className="meta-item">
                      <span className="meta-label">Date Placed</span>
                      <span className="meta-val">{formatDate(order.orderDate)}</span>
                    </div>
                    <div className="meta-item">
                      <span className="meta-label">Total Amount</span>
                      <span className="meta-val" style={{ color: "#fff" }}>
                        ₹{(order.totalAmount || 0).toLocaleString("en-IN")}
                      </span>
                    </div>
                    <div className="meta-item">
                      <span className="meta-label">Status</span>
                      <span className={`badge ${order.status === "PLACED" ? "badge-success" : "badge-danger"}`}>
                        {order.status}
                      </span>
                    </div>
                  </div>

                  <div style={{ display: "flex", gap: "12px" }}>
                    <button
                      onClick={() => toggleOrderDetails(order.id)}
                      className="btn btn-secondary btn-sm"
                    >
                      {isExpanded ? "Hide Details" : "View Items"}
                    </button>
                    {order.status === "PLACED" && (
                      <button
                        onClick={() => cancelOrder(order.id)}
                        className="btn btn-danger btn-sm"
                      >
                        Cancel Order
                      </button>
                    )}
                  </div>
                </div>

                {/* Collapsible Order Items Details */}
                {isExpanded && (
                  <div className="order-items-list">
                    {items.length === 0 ? (
                      <p style={{ color: "var(--text-secondary)", fontStyle: "italic" }}>
                        Loading purchase logs...
                      </p>
                    ) : (
                      items.map((item) => (
                        <div key={item.id} className="order-item-row">
                          <img
                            src={item.productImageUrl || "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&q=80"}
                            alt={item.productName || "Product"}
                            className="order-item-img"
                          />
                          <div className="order-item-info">
                            <span className="order-item-name">
                              {item.productName || `Product ID: ${item.productId}`}
                            </span>
                            <div className="order-item-price-qty">
                              Quantity: {item.quantity} &bull; Price: ₹{(item.price || 0).toLocaleString("en-IN")} each
                            </div>
                          </div>
                          <span className="meta-val" style={{ fontSize: "0.95rem" }}>
                            ₹{((item.price || 0) * item.quantity).toLocaleString("en-IN")}
                          </span>
                        </div>
                      ))
                    )}
                  </div>
                )}
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default Orders;
