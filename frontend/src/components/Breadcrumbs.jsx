import React from 'react';
import { Link, useLocation } from 'react-router-dom';

const Breadcrumbs = () => {
  const location = useLocation();
  const pathnames = location.pathname.split('/').filter((x) => x);

  return (
    <nav aria-label="breadcrumb" style={{ marginBottom: '1rem' }}>
      <ol style={{ listStyle: 'none', display: 'flex', padding: 0, margin: 0 }}>
        <li>
          <Link to="/" style={{ textDecoration: 'none' }}>
            Home
          </Link>
        </li>
        {pathnames.map((value, index) => {
          const to = `/${pathnames.slice(0, index + 1).join('/')}`;
          const isLast = index === pathnames.length - 1;
          return (
            <li key={to} style={{ marginLeft: '0.5rem' }}>
              <span>/</span>
              {isLast ? (
                <span style={{ marginLeft: '0.5rem' }}>{value}</span>
              ) : (
                <Link to={to} style={{ marginLeft: '0.5rem', textDecoration: 'none' }}>
                  {value}
                </Link>
              )}
            </li>
          );
        })}
      </ol>
    </nav>
  );
};

export default Breadcrumbs;
