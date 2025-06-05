import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import MainPage from './MainPage';

test('renders MainPage component', () => {
  render(<MainPage />);
});