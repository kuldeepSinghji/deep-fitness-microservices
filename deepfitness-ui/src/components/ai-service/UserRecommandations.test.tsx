import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import UserRecommandations from './UserRecommandations';
import * as api from '../../interceptors/api';

// Mock react-redux useSelector
jest.mock('react-redux', () => ({
  ...jest.requireActual('react-redux'),
  useSelector: jest.fn(() => 'test-activity-id'),
}));

// Mock getUserRecommandation API
jest.spyOn(api, 'getUserRecommandation').mockResolvedValue({
  data: {
    activityType: 'Running',
    recommendations: 'Stay hydrated.',
    fullAnalysisPerformanceMetrics: 'Good pace.',
    nextWorkoutSuggestions: ['Try intervals', 'Increase distance'],
    areasToImprove: ['Posture', 'Breathing'],
    safetyGuidelines: ['Warm up', 'Cool down'],
  },
  status: 200,
  statusText: 'OK',
  headers: {},
  config: { headers: new (require('axios').AxiosHeaders)() },
});

describe('UserRecommandations', () => {
  it('renders reload button and message when no recommendation', () => {
    // Override API to return no data
    jest.spyOn(api, 'getUserRecommandation').mockResolvedValueOnce({
      data: undefined,
      status: 200,
      statusText: 'OK',
      headers: {},
      config: { headers: new (require('axios').AxiosHeaders)() },
    });
    render(<UserRecommandations />);
    expect(screen.getByText(/please wait for a moment/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /reload/i })).toBeInTheDocument();
  });

  it('renders recommendations when data is present', async () => {
    render(<UserRecommandations />);
    // Wait for recommendations to appear
    expect(await screen.findByText(/recommendations/i)).toBeInTheDocument();
    expect(screen.getByText(/stay hydrated/i)).toBeInTheDocument();
    expect(screen.getByText(/good pace/i)).toBeInTheDocument();
    expect(screen.getByText(/try intervals/i)).toBeInTheDocument();
    expect(screen.getByText(/posture/i)).toBeInTheDocument();
    expect(screen.getByText(/warm up/i)).toBeInTheDocument();
  });

  it('calls getUserRecommandationWithAcivityId when reload button is clicked', () => {
    // Override API to return no data for first render
    jest.spyOn(api, 'getUserRecommandation').mockResolvedValueOnce({
      data: undefined,
      status: 200,
      statusText: 'OK',
      headers: {},
      config: { headers: new (require('axios').AxiosHeaders)() },
    });
    render(<UserRecommandations />);
    const reloadButton = screen.getByRole('button', { name: /reload/i });
    fireEvent.click(reloadButton);
    expect(api.getUserRecommandation).toHaveBeenCalled();
  });
});