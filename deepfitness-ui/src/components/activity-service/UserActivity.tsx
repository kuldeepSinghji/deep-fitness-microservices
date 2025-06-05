import React from 'react'
import type { userActivityData } from './UserActivityList';
import { Box, Button, Card, CardActions, CardContent, Modal, Typography } from '@mui/material';
import { useNavigate } from 'react-router';
import { useDispatch } from 'react-redux';
import { setActivityId } from '../../store/recommandationSlice';

export type ActivityCardProps = {
  activity: userActivityData;
  handleClickDeleteActivity: (activityId: string) => void;
};


const UserActivity: React.FC<ActivityCardProps> = ({ activity, handleClickDeleteActivity }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);


  const handleClickRecommandations = (activityId: string) => {
    localStorage.setItem('activityId', activityId);
    dispatch(setActivityId(activityId));
    navigate("/recommandations")
  }

  const handleDeleteActivity = (activityId:string)=>{
    handleClose();
    handleClickDeleteActivity(activityId);
  }


  const card = (
    <React.Fragment>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 400,
          border: '2px solid black',
          borderRadius:"8px",
          boxShadow: 24,
          p: 4,
          color:'black',
          backgroundColor:'white',
        }}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Message
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            Are you sure to delete this Activiy?
          </Typography>
          <Box sx={{m:2,display:"flex",justifyContent:"space-between"}}>
            <Button size="small" variant="contained" color="error"
              onClick={() => handleDeleteActivity(activity.activityId)}>Yes</Button>
            <Button size="small" variant="contained" color="primary" onClick={handleClose}>No</Button>
          </Box>
        </Box>
      </Modal>
      <CardContent>
        <Typography gutterBottom sx={{ color: 'text.secondary', fontSize: 14 }}>
          Activity Type: {activity.userActivityType || "Not Available"}
        </Typography>
        <Typography gutterBottom sx={{ color: 'text.secondary', fontSize: 14 }}>
          Gender: {activity.gender || "Not Available"}
        </Typography>
        <Typography gutterBottom sx={{ color: 'text.secondary', fontSize: 14 }}>
          Activity Start Time: {activity.activityStartTime.toString() || "Not Available"}
        </Typography>
        <Typography gutterBottom sx={{ color: 'text.secondary', fontSize: 14 }}>
          Activity Duration: {activity.activityDuration.toString() || "Not Available"} min
        </Typography>
        <Typography gutterBottom sx={{ color: 'text.secondary', fontSize: 14 }}>
          Calories Burned: {activity.caloriesBurned || "Not Available"}
        </Typography>
      </CardContent>
      <CardActions>
        <Button variant="outlined" color='secondary' size="small" onClick={() => handleClickRecommandations(activity.activityId)}>AI Recommandation</Button>
        <Button size="small" variant="outlined" color="error" onClick={handleOpen}
        //  onClick={() => handleClickDeleteActivity(activity.activityId)}
        >
          Delete Activity
        </Button>
      </CardActions>

    </React.Fragment>
  );

  return (
    <>
      <Card variant="outlined" sx={{ m: 2 }}>{card}</Card>
    </>
  )
}

export default UserActivity;