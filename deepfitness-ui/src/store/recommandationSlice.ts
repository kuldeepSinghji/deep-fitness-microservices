import { createSlice } from "@reduxjs/toolkit";

interface RecommendationsState {
  activityId: string; // you can replace `any` with the actual type if you have one
}

const initialState: RecommendationsState = {
  activityId: ""
};

export const recommendationsSlice = createSlice({
  name: 'recommendations', 
  initialState,           
  reducers: {
    clearRecommendations: (state) => {
      state.activityId = "";
    },
    setActivityId: (state, action) => {
      state.activityId = action.payload;
    },
  }
})

export const { clearRecommendations, setActivityId } = recommendationsSlice.actions;
export default recommendationsSlice.reducer;
export const getActivityId = (state: any) => state.recommendations.activityId;
