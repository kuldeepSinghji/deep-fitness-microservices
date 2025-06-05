import axiosHttp from "./verifyAuth";

export const verifyUser = () =>
  axiosHttp.get(`/users/validate/${localStorage.getItem('userId')}`);
export const registerActiviy = (data:any) => {
  return axiosHttp.post(`/activity/register-user-activity`, data);
};
export const getUserActivities = (userId:string)=> axiosHttp.get(`/activity/user-activities`,{headers:{
  'X-USER-ID':userId
}});

export const getUserRecommandation = (activityId:string)=> axiosHttp.get(`ai/recommendations/activity/${activityId}`);
export const removeUserActivity = (activityId:string)=> axiosHttp.delete(`activity/${activityId}`);