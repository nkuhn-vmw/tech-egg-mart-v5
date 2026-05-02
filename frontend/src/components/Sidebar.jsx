import React from 'react';
import { NavLink } from 'react-router-dom';

const Sidebar = () => (
  <aside style={{ width: '200px', background: '#f4f4f4', padding: '1rem' }}>
    <h3>Categories</h3>
    <ul style={{ listStyle: 'none', padding: 0 }}>
      <li>
        <NavLink to="/categories" style={{ textDecoration: 'none', color: '#333' }}>
          All Categories
        </NavLink>
      </li>
      {/* Add dynamic category links here in the future */}
    </ul>
  </aside>
);

export default Sidebar;
