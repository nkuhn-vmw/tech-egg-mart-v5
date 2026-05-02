import React from 'react';
import { Link } from 'react-router-dom';

const Navigation = () => (
  <nav style={{ padding: '1rem', background: '#f0f0f0', marginBottom: '1rem' }}>
    <Link to="/" style={{ margin: '0 1rem', textDecoration: 'none', color: 'black' }}>Home</Link>
    <Link to="/products" style={{ margin: '0 1rem', textDecoration: 'none', color: 'black' }}>Products</Link>
    <Link to="/categories" style={{ margin: '0 1rem', textDecoration: 'none', color: 'black' }}>Categories</Link>
    <Link to="/reviews" style={{ margin: '0 1rem', textDecoration: 'none', color: 'black' }}>Reviews</Link>
  </nav>
);

export default Navigation;