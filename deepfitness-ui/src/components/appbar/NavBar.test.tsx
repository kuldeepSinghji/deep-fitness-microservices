import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import NavBar from './NavBar';
import { MemoryRouter } from 'react-router';

// Mock useNavigate from react-router
const mockNavigate = jest.fn();
jest.mock('react-router', () => ({
  ...jest.requireActual('react-router'),
  useNavigate: () => mockNavigate,
}));

describe('NavBar', () => {
  beforeEach(() => {
    mockNavigate.mockClear();
  });

  it('renders all nav items when token is present', () => {
    render(
      <MemoryRouter>
        <NavBar token={true} />
      </MemoryRouter>
    );
    expect(screen.getAllByText(/home/i)[0]).toBeInTheDocument();
    expect(screen.getAllByText(/about/i)[0]).toBeInTheDocument();
    expect(screen.getAllByText(/contact/i)[0]).toBeInTheDocument();
    expect(screen.getAllByText(/logout/i)[0]).toBeInTheDocument();
  });

  it('renders Login when token is not present', () => {
    render(
      <MemoryRouter>
        <NavBar />
      </MemoryRouter>
    );
    expect(screen.getAllByText(/login/i)[0]).toBeInTheDocument();
  });

  it('calls logOut when Logout is clicked', () => {
    const logOut = jest.fn();
    render(
      <MemoryRouter>
        <NavBar token={true} logOut={logOut} />
      </MemoryRouter>
    );
    fireEvent.click(screen.getAllByRole('button', { name: /logout/i })[0]);
    expect(logOut).toHaveBeenCalled();
  });

  it('calls logIn when Login is clicked', () => {
    const logIn = jest.fn();
    render(
      <MemoryRouter>
        <NavBar logIn={logIn} />
      </MemoryRouter>
    );
    // Use getAllByRole to ensure you click the button, not a span
    fireEvent.click(screen.getAllByRole('button', { name: /login/i })[0]);
    expect(logIn).toHaveBeenCalled();
  });

  it('navigates to home when Home is clicked', () => {
    render(
      <MemoryRouter>
        <NavBar token={true} />
      </MemoryRouter>
    );
    fireEvent.click(screen.getAllByRole('button', { name: /home/i })[0]);
    expect(mockNavigate).toHaveBeenCalledWith('/');
  });

  it('navigates to about when About is clicked', () => {
    render(
      <MemoryRouter>
        <NavBar token={true} />
      </MemoryRouter>
    );
    fireEvent.click(screen.getAllByRole('button', { name: /about/i })[0]);
    expect(mockNavigate).toHaveBeenCalledWith('/about');
  });

  it('navigates to contact when Contact is clicked', () => {
    render(
      <MemoryRouter>
        <NavBar token={true} />
      </MemoryRouter>
    );
    fireEvent.click(screen.getAllByRole('button', { name: /contact/i })[0]);
    expect(mockNavigate).toHaveBeenCalledWith('/contact');
  });
});
