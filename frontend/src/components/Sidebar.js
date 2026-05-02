import React from 'react';

const Sidebar = () => (
  <aside style={{ width: '250px', padding: '1rem', background: '#e8e8e8', height: '100%' }}>
    <h3>Filters</h3>
    <div>
      <h4>Categories</h4>
      <ul>
        <li>All Products</li>
        <li>Electronics</li>
        <li>Books</li>
        <li>Clothing</li>
      </ul>
    </div>
    <div>
      <h4>Price Range</h4>
      <div>
        <label>Min: $0</label><br/>
        <label>Max: $1000</label>
      </div>
    </div>
  </aside>
);

export default Sidebar;