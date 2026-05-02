import React from 'react';
import Navigation from '../components/Navigation';

const Home = () => (
  <div>
    <Navigation />
    <div style={{ padding: '2rem', textAlign: 'center' }}>
      <h1>Welcome to Tech Egg Mart</h1>
      <p>Your one-stop shop for all tech products</p>
    </div>
  </div>
);

export default Home;