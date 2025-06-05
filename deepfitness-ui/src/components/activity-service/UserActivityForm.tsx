import { Alert, Autocomplete, Box, Button, TextField } from "@mui/material";
import { ACTIVITY_TYPES } from "./activity-types";
import { useEffect, useState } from "react";
import { getUserActivities, registerActiviy, removeUserActivity, verifyUser } from "../../interceptors/api";
import UserActivityList, { type userActivityData } from "./UserActivityList";


type activityFormType = {
  activityType: string,
  caloriesBurned: number|string,
  activityDuration: number|string,
  gender: string,
  activityStartTime: Date ,
  distance: number|string,
  maxHeartRate: number|string,
  averageSpeed: number|string
}

const defaultActivityForm: activityFormType = { activityType: "", caloriesBurned: '', activityDuration: '', gender: "", activityStartTime: new Date(), averageSpeed: '', maxHeartRate: '', distance: '' };

const UserActivityForm = () => {
  const [activityForm, setActivityForm] = useState(defaultActivityForm);
  const [formValidation, setFormValidation] = useState(true);
  const [successAlert, setSuccessAlert] = useState({formSuccess:false,deleteSuccess:false});

  const [activities, setActivities] = useState<userActivityData[]>([]);


  useEffect(() => {
    fetchActivities()
  }, [])

  const fetchActivities = async () => {
    const userId = localStorage.getItem('userId') || "";
    if (!userId) return; // Optional: handle missing userId
    try {
      const activitiesRes: any = await getUserActivities(userId);
      setActivities(activitiesRes?.data);
    } catch (error) {
      console.error('Failed to fetch user activities:', error);
    }

  }
  const validateNumberValue = (value: any) => {
    if (/^\d*$/.test(value)) {
      return value;
    } else {
      return '';
    }
  }
  const handleActivityFormChange = (event: React.SyntheticEvent,
    value: any | null, name: string) => {
    if (name === 'caloriesBurned' || name === 'activityDuration') {
      value = validateNumberValue(value);
      setActivityForm((prev) => { return { ...prev, [name]: value } })
    } else if (name === "activityStartTime") {
      const [hours, minutes] = value.split(":").map(Number);
      const newDate = new Date(); // default to today
      newDate.setHours(hours);
      newDate.setMinutes(minutes);
      newDate.setSeconds(0);
      setActivityForm({ ...activityForm, [name]: newDate });
    } else {
      setActivityForm((prev) => { return { ...prev, [name]: value } })
    }
  }


  function formatTime(date: Date | string | null | undefined): string {
    if (!(date instanceof Date)) {
      try {
        date = new Date(date as string);
      } catch {
        return '';
      }
    }

    if (isNaN(date.getTime())) return ''; 

    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
  }

  const handleSubmit = async() => {
     const isFormValid = activityForm.activityType && activityForm.activityDuration && activityForm.gender;

    if (!isFormValid) {
      setFormValidation(false);
      window.scrollTo({top: 0, behavior: 'smooth'});
      return;
    }
    setFormValidation(true);
    const isValid =await callUserVerifyAPI();
      if(isValid){
        const requestBody = {
          "userId": localStorage.getItem('userId'),
          'additionalMetrics':{
            "distance":activityForm.distance,
            "maxHeartRate":activityForm.maxHeartRate,
            "averageSpeed":activityForm.averageSpeed 
          },
          "userActivityType":activityForm.activityType,
          ...activityForm
        }
         window.scrollTo({top: 0, behavior: 'smooth'});
        await callRegisterUserActivityAPI(requestBody);
        setActivityForm(defaultActivityForm);
        setSuccessAlert({...successAlert,formSuccess:true});
    }
    
  }

  const callRegisterUserActivityAPI = async (requestBody:Object)=>{
    const response =  await registerActiviy(requestBody);
    if(response.data){
      setActivities([...activities,response.data])
    }
  }

  const callUserVerifyAPI = async ()=>{
    const response = await verifyUser();
    return response.data;
  }

  const handleAlert = (type:string) => {
    if(type === 'error'){
      setFormValidation(true)
    }else{
      setSuccessAlert({deleteSuccess:false,formSuccess:false});
    }
  }

  const handleClickDeleteActivity = async (activityId: string) => {
    localStorage.removeItem('activityId');
    let res = await callDeleteActivityAPI(activityId);
    if(res.data){
      const act = activities?.filter((activity:userActivityData) => res.data != activity?.activityId);
      setActivities(act);
      window.scrollTo({top: 0, behavior: 'smooth'});
      setSuccessAlert({...successAlert,deleteSuccess:true});
    }
  }
  
  const callDeleteActivityAPI = async (activityId: string) => {
    return await removeUserActivity(activityId);
  }

  return (
    <>
      <Box component="section" sx={{ p: 2,m:2, border: '1px solid grey' }}>
        {!formValidation && <Alert variant="filled" severity="error" onClose={() => { handleAlert("error") }}>
          The highlighted filed can not be empty. Please add..
        </Alert>}
        {(successAlert.formSuccess || successAlert.deleteSuccess)  && <Alert variant="filled" severity="success" onClose={() => { handleAlert("success") }}>
          {successAlert.formSuccess ? "Success: Form is successfully submited." : "Success: Activity deleted successfully."}
        </Alert>}
        <Box component="section" sx={{ p: 2, margin: '5px 0px' }}>
          <Autocomplete
            value={activityForm.activityType}
            disablePortal
            renderValue={()=> activityForm.activityType}
            options={ACTIVITY_TYPES}
            sx={{ width: "100%", border: `${!activityForm.activityType && !formValidation ? "2px solid red" : ""}` }}
            onChange={(e, v) => handleActivityFormChange(e, v, "activityType")}
            renderInput={(params) => <TextField {...params} label="Activity Type" />} />
        </Box>
        <Box component="section" sx={{ p: 2, margin: '5px 0px' }}>
          <TextField sx={{ width: "100%" }} value={validateNumberValue(activityForm.caloriesBurned)} onChange={(e) => handleActivityFormChange(e, e.target.value, "caloriesBurned")} label="Calories Burned(Number)" />
        </Box>
        <Box component="section" sx={{ p: 2, margin: '5px 0px' }}>
          <TextField sx={{ width: "100%", border: `${!activityForm.activityDuration && !formValidation ? "2px solid red" : ""}` }} value={validateNumberValue(activityForm.activityDuration)} onChange={(e) => handleActivityFormChange(e, e.target.value, "activityDuration")} label="Duration(Minutes)" />
        </Box>
        <Box component="section" sx={{ p: 2, margin: '5px 0px' }}>
          <Autocomplete
            disablePortal
            options={["Male", "Female", "Other"]}
            renderValue={()=> activityForm.gender}
            sx={{ width: "100%", border: `${!activityForm.gender && !formValidation ? "2px solid red" : ""}` }}
            onChange={(e, v) => handleActivityFormChange(e, v, "gender")}
            renderInput={(params) => <TextField {...params} label="Gender" />} />
        </Box>
        <Box component="section" sx={{ p: 2, margin: '5px 0px' }}>
          <label style={{color:"grey"}} htmlFor="activityStartTime">Start Time</label>
          <input id="activityStartTime" onChange={(e) => handleActivityFormChange(e, e.target.value, "activityStartTime")} placeholder="Activity Start Time" value={formatTime(activityForm.activityStartTime)} style={{ width: "100%", border: "1px solid grey", borderRadius: "4px", padding: "1%", fontWeight: "bolder" }} type="time" />
        </Box>
        <Box component="section" sx={{ p: 2, margin: '5px 0px', display: "flex", justifyContent: "space-between", flexWrap: "wrap" }}>
          <TextField sx={{ m: 1,}} value={validateNumberValue(activityForm.distance)} onChange={(e) => handleActivityFormChange(e, e.target.value, "distance")} label="Distance(meters)" />
          <TextField sx={{ m: 1,}} value={validateNumberValue(activityForm.averageSpeed)} onChange={(e) => handleActivityFormChange(e, e.target.value, "averageSpeed")} label="Average Speed(km/h)" />
          <TextField sx={{ m: 1,}} value={validateNumberValue(activityForm.maxHeartRate)} onChange={(e) => handleActivityFormChange(e, e.target.value, "maxHeartRate")} label="Max Heart Rate" />
        </Box>
        <Box component="section" sx={{ p: 2, margin: '5px 0px' }}>
          <Button variant="contained" onClick={handleSubmit}>Add Activity</Button>
        </Box>
      </Box>
      <UserActivityList activities={activities}  handleClickDeleteActivity={handleClickDeleteActivity}/>
    </>
  )
}

export default UserActivityForm