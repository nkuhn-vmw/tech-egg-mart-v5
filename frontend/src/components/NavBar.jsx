import React from 'react';
import { NavLink } from 'react-router-dom';

const NavBar = () => (
  <nav style={{ background: '#333', padding: '0.5rem' }}>
    <ul style={{ display: 'flex', listStyle: 'none', margin: 0, padding: 0 }}>
      <li style={{ marginRight: '1rem' }}>
        <NavLink to="/" style={{ color: '#fff', textDecoration: 'none' }} end>
          Home
        </NavLink>
      </li>
      <li style={{ marginRight: '1rem' }}>
        <NavLink to="/products" style={{ color: '#fff', textDecoration: 'none' }}>
          Products
        </NavLink>
      </li>
      <li style={{ marginRight: '1rem' }}>
        <NavLink to="/categories" style={{ color: '#fff', textDecoration: 'none' }}>
          Categories
        </NavLink>
      </li>
      <li>
        <NavLink to="/reviews" style={{ color: '#fff', textDecoration: 'none' }}>
          Reviews
        </NavLink>
      </li>
    </ul>
  </nav>
);

export default NavBar;
