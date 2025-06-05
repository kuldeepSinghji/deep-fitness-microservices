import reducer, {
  clearRecommendations,
  setActivityId,
  getActivityId
} from './recommandationSlice';

describe('recommendationsSlice', () => {
  const initialState = { activityId: '' };

  it('should return the initial state', () => {
    expect(reducer(undefined, { type: '' })).toEqual(initialState);
  });

  it('should handle setActivityId', () => {
    const action = setActivityId('abc123');
    const state = reducer(initialState, action);
    expect(state.activityId).toBe('abc123');
  });

  it('should handle clearRecommendations', () => {
    const stateWithId = { activityId: 'some-id' };
    const state = reducer(stateWithId, clearRecommendations());
    expect(state.activityId).toBe('');
  });

  it('getActivityId selector should return activityId', () => {
    const mockState = { recommendations: { activityId: 'test-id' } };
    expect(getActivityId(mockState)).toBe('test-id');
  });
});