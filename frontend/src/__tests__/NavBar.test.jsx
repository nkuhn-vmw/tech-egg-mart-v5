import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import NavBar from '../components/NavBar';

describe('NavBar component', () => {
  test('renders navigation links', () => {
    render(
      <BrowserRouter>
        <NavBar />
      </BrowserRouter>
    );
    // Check that each link text is present
    expect(screen.getByText('Home')).toBeInTheDocument();
    expect(screen.getByText('Products')).toBeInTheDocument();
    expect(screen.getByText('Categories')).toBeInTheDocument();
    expect(screen.getByText('Reviews')).toBeInTheDocument();
  });
});
