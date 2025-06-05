import { configureStore } from "@reduxjs/toolkit";
import { authSlice } from "./authSlice";
import { recommendationsSlice } from "./recommandationSlice";

export const store = configureStore({
    reducer: {
        auth: authSlice.reducer,
        recommendations: recommendationsSlice.reducer
    }
})