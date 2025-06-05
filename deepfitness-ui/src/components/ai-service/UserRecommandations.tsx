import { Box, Button, Typography } from '@mui/material';
import { getActivityId } from '../../store/recommandationSlice';
import { useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { getUserRecommandation } from '../../interceptors/api';



export type userRecommandationType = {
  activityType: string
  recommendations: string
  fullAnalysisPerformanceMetrics: string
  nextWorkoutSuggestions: string[]
  areasToImprove: string[]
  safetyGuidelines: string[]
}


const UserRecommandations = () => {
  const activityIdFromStore = useSelector(getActivityId);
  const [userRecommandation, setUserRecommandation] = useState<userRecommandationType>();
  const getUserRecommandationWithAcivityId = async (activityId: string) => {
    activityId = activityId || localStorage.getItem("activityId") || "";
    if (activityId) {
      const res = await getUserRecommandation(activityId);
      if (res.data) {
        setUserRecommandation(res.data);
      }
    }
  }

  useEffect(() => {
    getUserRecommandationWithAcivityId(activityIdFromStore);
  }, [])

  return (
    <>
      {userRecommandation?.activityType && <Box component="section" sx={{ p: 2, mx: 2, display: "flex", flexWrap: "wrap", justifyContent: "flex-start" }}>
        <Box component="section" sx={{ marginBottom: "15px", width: "100%", p: 2, border: '1px solid grey' }}>
          <Typography variant='h5' sx={{ textTransform: "uppercase", fontWeight: "bolder" }}>recommendations</Typography>
          <Typography>
            <pre style={{ whiteSpace: 'pre-wrap', fontFamily: "Roboto", fontSize: "18px", letterSpacing: "-0.4px" }}>{userRecommandation?.recommendations}</pre>
          </Typography>
        </Box>
        <Box component="section" sx={{ marginBottom: "15px", width: "100%", p: 2, border: '1px solid grey' }}>
          <Typography variant='h5' sx={{ textTransform: "uppercase", fontWeight: "bolder" }}>Full Analysis Performance Metrics</Typography>
          <Typography>
            <pre style={{ whiteSpace: 'pre-wrap', fontFamily: "Roboto", fontSize: "18px", letterSpacing: "-0.4px" }}>{userRecommandation?.fullAnalysisPerformanceMetrics}</pre>
          </Typography>
        </Box>
        <Box component="section" sx={{ marginBottom: "15px", width: "100%", p: 2, border: '1px solid grey' }}>
          <Typography variant='h5' sx={{ textTransform: "uppercase", fontWeight: "bolder" }}>Next Workout Suggestions</Typography>
          <Typography>
            {userRecommandation?.nextWorkoutSuggestions.map((item, key) => {
              return <pre style={{ whiteSpace: 'pre-wrap', fontFamily: "Roboto", fontSize: "18px", letterSpacing: "-0.4px" }} key={key}>{item}</pre>
            })}
          </Typography>
        </Box>
        <Box component="section" sx={{ marginBottom: "15px", width: "100%", p: 2, border: '1px solid grey' }}>
          <Typography variant='h5' sx={{ textTransform: "uppercase", fontWeight: "bolder" }}>Areas To Improve</Typography>
          <Typography>
            {userRecommandation?.areasToImprove.map((item, key) => {
              return <pre style={{ whiteSpace: 'pre-wrap', fontFamily: "Roboto", fontSize: "18px", letterSpacing: "-0.4px" }} key={key}>{item}</pre>
            })}
          </Typography>
        </Box>
        <Box component="section" sx={{ marginBottom: "15px", width: "100%", p: 2, border: '1px solid grey' }}>
          <Typography variant='h5' sx={{ textTransform: "uppercase", fontWeight: "bolder" }}>Safety Guidelines</Typography>
          <Typography>
            {userRecommandation?.safetyGuidelines.map((item, key) => {
              return <pre style={{ whiteSpace: 'pre-wrap', fontFamily: "Roboto", fontSize: "18px", letterSpacing: "-0.4px" }} key={key}>{item}</pre>
            })}
          </Typography>
        </Box>
      </Box>}
      <Box component="section" sx={{ marginBottom: "15px", width: "100%", p: 2 }}>
        {!userRecommandation?.activityType && <Typography>
          Please wait for a moment and click on the<Button onClick={() => getUserRecommandationWithAcivityId(activityIdFromStore)}>Reload</Button>button.
        </Typography>}
      </Box>
    </>
  )
}

export default UserRecommandations;