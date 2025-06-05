import { store } from './store';
import { setCredentials, logout } from './authSlice';
import { setActivityId, clearRecommendations } from './recommandationSlice';

describe('Redux store', () => {
  afterEach(() => {
    store.dispatch(logout());
    store.dispatch(clearRecommendations());
  });

  it('should have auth and recommendations slices', () => {
    const state = store.getState();
    expect(state).toHaveProperty('auth');
    expect(state).toHaveProperty('recommendations');
  });

  it('should update auth state with setCredentials and logout', () => {
    const payload = {
      user: { name: 'Test User', sub: '123' },
      token: 'test-token',
      sub: '123',
    };
    store.dispatch(setCredentials(payload));
    let state = store.getState();
    expect(state.auth.user).toEqual(payload.user);
    expect(state.auth.token).toBe(payload.token);
    expect(state.auth.userId).toBe(payload.sub);

    store.dispatch(logout());
    state = store.getState();
    expect(state.auth.user).toBeNull();
    expect(state.auth.token).toBeNull();
    expect(state.auth.userId).toBeNull();
  });

  it('should update recommendations state with setActivityId and clearRecommendations', () => {
    store.dispatch(setActivityId('activity-xyz'));
    let state = store.getState();
    expect(state.recommendations.activityId).toBe('activity-xyz');

    store.dispatch(clearRecommendations());
    state = store.getState();
    expect(state.recommendations.activityId).toBe('');
  });
});