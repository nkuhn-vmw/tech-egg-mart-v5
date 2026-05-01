import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

const Home = () => <h2>Home</h2>;
const Products = () => <h2>Products</h2>;
const Categories = () => <h2>Categories</h2>;
const Reviews = () => <h2>Reviews</h2>;

const App = () => (
  <Router>
    <nav style={{ padding: '1rem', borderBottom: '1px solid #ccc' }}>
      <Link to="/" style={{ marginRight: '1rem' }}>Home</Link>
      <Link to="/products" style={{ marginRight: '1rem' }}>Products</Link>
      <Link to="/categories" style={{ marginRight: '1rem' }}>Categories</Link>
      <Link to="/reviews">Reviews</Link>
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
