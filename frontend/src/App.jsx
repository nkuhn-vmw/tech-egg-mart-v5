import React from 'react';
import { BrowserRouter as Router, Routes, Route, Outlet } from 'react-router-dom';
import NavBar from './components/NavBar';
import Sidebar from './components/Sidebar';
import Breadcrumbs from './components/Breadcrumbs';
import Home from './pages/Home';
import Products from './pages/Products';
import Categories from './pages/Categories';
import Reviews from './pages/Reviews';

const Layout = () => (
  <div style={{ display: 'flex', flexDirection: 'column', height: '100vh' }}>
    <NavBar />
    <div style={{ display: 'flex', flex: 1, overflow: 'hidden' }}>
      <Sidebar />
      <main style={{ flex: 1, padding: '1rem', overflowY: 'auto' }}>
        <Breadcrumbs />
        <Outlet />
      </main>
    </div>
  </div>
);

const App = () => (
  <Router>
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="products" element={<Products />} />
        <Route path="categories" element={<Categories />} />
        <Route path="reviews" element={<Reviews />} />
      </Route>
    </Routes>
  </Router>
);

export default App;
