import React, { useState, useEffect } from 'react';
import Navigation from '../components/Navigation';
import Breadcrumb from '../components/Breadcrumb';

const ProductDetail = ({ match }) => {
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Simulate API call
    const fetchProduct = async () => {
      try {
        // In a real app, this would be:
        // const response = await fetch(`/api/products/${match.params.id}`);
        // const data = await response.json();
        
        // Mock data for demonstration
        setTimeout(() => {
          setProduct({
            id: 1,
            name: 'Sample Product',
            description: 'This is a sample product description.',
            price: 29.99,
            brand: 'TechBrand',
            model: 'ModelX',
            specifications: 'High quality product with advanced features',
            stockQuantity: 10,
            averageRating: 4.5,
            reviewCount: 120
          });
          setLoading(false);
        }, 500);
      } catch (err) {
        setError('Failed to fetch product');
        setLoading(false);
      }
    };

    fetchProduct();
  }, [match]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <Navigation />
      <Breadcrumb currentPage="Product Detail" />
      <div style={{ padding: '2rem' }}>
        <h1>{product.name}</h1>
        <div style={{ display: 'flex', gap: '2rem' }}>
          <div>
            <img 
              src="/placeholder.jpg" 
              alt={product.name} 
              style={{ width: '300px', height: '300px', objectFit: 'cover' }} 
            />
          </div>
          <div>
            <p><strong>Brand:</strong> {product.brand}</p>
            <p><strong>Model:</strong> {product.model}</p>
            <p><strong>Price:</strong> ${product.price}</p>
            <p><strong>Rating:</strong> {product.averageRating} ({product.reviewCount} reviews)</p>
            <p><strong>In Stock:</strong> {product.stockQuantity} units</p>
            <button>Add to Cart</button>
          </div>
        </div>
        <div style={{ marginTop: '2rem' }}>
          <h3>Description</h3>
          <p>{product.description}</p>
          
          <h3>Specifications</h3>
          <p>{product.specifications}</p>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;