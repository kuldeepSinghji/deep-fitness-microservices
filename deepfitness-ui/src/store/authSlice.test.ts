import authReducer, { setCredentials, logout } from './authSlice';

describe('authSlice', () => {
  const initialState = {
    user: null,
    token: null,
    userId: null,
  };

  beforeEach(() => {
    localStorage.clear();
  });

  it('should return the initial state', () => {
    expect(authReducer(undefined, { type: '' })).toEqual({
      user: null,
      token: null,
      userId: null,
    });
  });

  it('should handle setCredentials', () => {
    const payload = {
      user: { name: 'Test User', sub: '123' },
      token: 'test-token',
      sub: '123',
    };
    const nextState = authReducer(initialState, setCredentials(payload));
    expect(nextState.user).toEqual(payload.user);
    expect(nextState.token).toBe(payload.token);
    expect(nextState.userId).toBe(payload.sub);

    // Check localStorage side effects
    expect(localStorage.getItem('user')).toBe(JSON.stringify(payload.user));
    expect(localStorage.getItem('token')).toBe(payload.token);
    expect(localStorage.getItem('userId')).toBe(payload.user.sub);
  });

  it('should handle logout', () => {
    // Set up state and localStorage as if logged in
    const loggedInState = {
      user: { name: 'Test User', sub: '123' },
      token: 'test-token',
      userId: '123',
    };
    localStorage.setItem('user', JSON.stringify(loggedInState.user));
    localStorage.setItem('token', loggedInState.token);
    localStorage.setItem('userId', loggedInState.userId);

    const nextState = authReducer(loggedInState, logout());
    expect(nextState.user).toBeNull();
    expect(nextState.token).toBeNull();
    expect(nextState.userId).toBeNull();

    // Check localStorage side effects
    expect(localStorage.getItem('user')).toBeNull();
    expect(localStorage.getItem('token')).toBeNull();
    expect(localStorage.getItem('userId')).toBeNull();
  });
});