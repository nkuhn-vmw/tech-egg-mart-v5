import React, { useState, useEffect } from 'react';
import Navigation from '../components/Navigation';
import Sidebar from '../components/Sidebar';
import Breadcrumb from '../components/Breadcrumb';

const Products = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Simulate API call
    const fetchProducts = async () => {
      try {
        // In a real app, this would be:
        // const response = await fetch('/api/products');
        // const data = await response.json();
        
        // Mock data for demonstration
        setTimeout(() => {
          setProducts([
            { id: 1, name: 'Product 1', price: 29.99, imageUrl: '/placeholder.jpg' },
            { id: 2, name: 'Product 2', price: 39.99, imageUrl: '/placeholder.jpg' },
            { id: 3, name: 'Product 3', price: 49.99, imageUrl: '/placeholder.jpg' }
          ]);
          setLoading(false);
        }, 500);
      } catch (err) {
        setError('Failed to fetch products');
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <Navigation />
      <Breadcrumb currentPage="Products" />
      <div style={{ display: 'flex' }}>
        <Sidebar />
        <main style={{ flex: 1, padding: '1rem' }}>
          <h2>Product Listing</h2>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))', gap: '1rem' }}>
            {products.map(product => (
              <div key={product.id} style={{ border: '1px solid #ddd', padding: '1rem' }}>
                <img src={product.imageUrl} alt={product.name} style={{ width: '100%', height: '150px', objectFit: 'cover' }} />
                <h3>{product.name}</h3>
                <p>${product.price}</p>
                <button>Add to Cart</button>
              </div>
            ))}
          </div>
        </main>
      </div>
    </div>
  );
};

export default Products;