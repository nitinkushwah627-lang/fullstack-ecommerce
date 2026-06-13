import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function ProductList({ user, onCartUpdated }) {
  const [products, setProducts] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/products");
      setProducts(res.data);
    } catch (error) {
      console.error("Failed to load products:", error);
    } finally {
      setLoading(false);
    }
  };

  const addToCart = async (productId) => {
    if (!user) {
      alert("🔑 Please login to add items to your cart!");
      navigate("/login");
      return;
    }

    const cartPayload = {
      userId: user.id,
      productId: productId,
      quantity: 1,
    };

    try {
      await axios.post("http://localhost:8080/api/cart", cartPayload);
      onCartUpdated();
      alert("✅ Product added to cart successfully!");
    } catch (error) {
      console.error(error);
      alert("❌ Error adding product to cart.");
    }
  };

  const filteredProducts = products.filter((p) =>
    p.name.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="container">
      {/* Search Header */}
      <div className="shop-header">
        <h1 className="shop-title">🛍️ Explore Products</h1>
        <div className="search-bar">
          <input
            type="text"
            className="form-input"
            placeholder="Search products..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </div>
      </div>

      {loading ? (
        <div className="empty-state">
          <h3>Loading catalog items...</h3>
        </div>
      ) : filteredProducts.length === 0 ? (
        <div className="empty-state">
          <div className="empty-icon">🔍</div>
          <h3>No products match your search.</h3>
          <p style={{ color: "var(--text-secondary)", marginTop: "8px" }}>
            Try searching for something else!
          </p>
        </div>
      ) : (
        <div className="products-grid">
          {filteredProducts.map((product) => (
            <div key={product.id} className="product-card glass-panel">
              <div 
                className="product-img-wrapper"
                onClick={() => setSelectedProduct(product)}
                style={{ cursor: "pointer" }}
              >
                <img
                  src={product.imageUrl || "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&q=80"}
                  alt={product.name}
                  className="product-img"
                />
              </div>

              <div className="product-body">
                <h3 
                  className="product-title"
                  onClick={() => setSelectedProduct(product)}
                  style={{ cursor: "pointer" }}
                >
                  {product.name}
                </h3>
                <p className="product-desc">{product.description}</p>
                
                <div className="product-footer">
                  <span className="product-price">₹{product.price.toLocaleString("en-IN")}</span>
                  <button
                    onClick={() => addToCart(product.id)}
                    className="btn btn-primary btn-sm"
                  >
                    Add To Cart
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Product Details Modal */}
      {selectedProduct && (
        <div className="modal-overlay" onClick={() => setSelectedProduct(null)}>
          <div className="modal-content glass-panel" onClick={(e) => e.stopPropagation()}>
            <button className="modal-close" onClick={() => setSelectedProduct(null)}>
              &times;
            </button>
            <img
              src={selectedProduct.imageUrl}
              alt={selectedProduct.name}
              className="modal-img"
            />
            <h2 className="modal-title">{selectedProduct.name}</h2>
            <p className="badge badge-primary" style={{ margin: "8px 0 16px 0" }}>Premium</p>
            <p className="modal-desc">{selectedProduct.description}</p>
            
            <div className="modal-footer">
              <span className="product-price" style={{ fontSize: "1.8rem" }}>
                ₹{selectedProduct.price.toLocaleString("en-IN")}
              </span>
              <div style={{ display: "flex", gap: "12px" }}>
                <button
                  onClick={() => setSelectedProduct(null)}
                  className="btn btn-secondary"
                >
                  Close
                </button>
                <button
                  onClick={() => {
                    addToCart(selectedProduct.id);
                    setSelectedProduct(null);
                  }}
                  className="btn btn-primary"
                >
                  Add To Cart
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ProductList;