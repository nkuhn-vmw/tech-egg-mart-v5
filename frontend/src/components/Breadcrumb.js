import React from 'react';
import { Link } from 'react-router-dom';

const Breadcrumb = ({ currentPage }) => (
  <nav style={{ padding: '1rem', background: '#f8f8f8' }}>
    <Link to="/" style={{ textDecoration: 'none', color: 'blue' }}>Home</Link> / 
    <span style={{ color: 'gray' }}> {currentPage}</span>
  </nav>
);

export default Breadcrumb;