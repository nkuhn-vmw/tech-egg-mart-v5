import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import Products from './pages/Products';
import Categories from './pages/Categories';
import Reviews from './pages/Reviews';

const App = () => (
  <Router>
    <nav style={{ padding: '1rem', background: '#f0f0f0' }}>
      <Link to="/" style={{ margin: '0 1rem' }}>Home</Link>
      <Link to="/products" style={{ margin: '0 1rem' }}>Products</Link>
      <Link to="/categories" style={{ margin: '0 1rem' }}>Categories</Link>
      <Link to="/reviews" style={{ margin: '0 1rem' }}>Reviews</Link>
    </nav>
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/products" element={<Products />} />
      <Route path="/categories" element={<Categories />} />
      <Route path="/reviews" element={<Reviews />} />
    </Routes>
  </Router>
);

export default App;
