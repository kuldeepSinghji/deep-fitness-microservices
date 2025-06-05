import { createSlice } from '@reduxjs/toolkit'

const user = localStorage.getItem('user');
export const authSlice = createSlice({
    name: 'auth',
    initialState: {
        user: user ? JSON.parse(user) : null,
        token: localStorage.getItem('token') || null,
        userId: localStorage.getItem('userId') || null
    },
    reducers: {
        setCredentials: (state, action) => {
            state.user = action.payload.user;
            state.token = action.payload.token;
            state.userId = action.payload.sub;
            localStorage.setItem('user', JSON.stringify(action.payload.user));
            localStorage.setItem('userId', action.payload.user.sub)
            localStorage.setItem('token', action.payload.token);
        },
        logout: (state) => {
            state.user = null;
            state.token = null;
            state.userId = null;
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            localStorage.removeItem('userId');
        }
    },
})

export const { setCredentials, logout } = authSlice.actions

export default authSlice.reducer