import { render} from '@testing-library/react';
import '@testing-library/jest-dom';
import Home from './Home';

jest.mock('../interceptors/api'); // <-- adjust the path as needed

test('renders Home component', async () => {
    const res = await verifyUser();
    const data = res.data;
    render(<Home isAuthReady={true} logIn={jest.fn()} />);
});
function verifyUser() {
    return Promise.resolve({ data: { user: { id: 1, name: 'Test User' } } });
}
