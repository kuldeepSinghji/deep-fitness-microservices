
import { useEffect, useState } from 'react'
import {  removeUserActivity } from '../../interceptors/api'
import UserActivity from './UserActivity'
import { Box, Typography } from '@mui/material'

export type userActivityData = {
  activityId: string,
  userActivityType: string,
  activityDuration: Date,
  caloriesBurned: number | string,
  activityStartTime: Date,
  createDate: Date,
  lastUpdateDate: Date,
  additionalMetrics: additionalMetrics,
  gender: string
}

type additionalMetrics = {
  distancedistance: number | string,
  maxHeartRate: number | string,
  averageSpeed: number | string
}

const UserActivityList = (props: any) => {
  const { activities, handleClickDeleteActivity } = props;
  

  return (
    <>
      <Typography sx={{ paddingLeft: 2, paddingTop: 2, fontSize: "2rem" }} >Activities</Typography>
      <Box component="section" sx={{ display: "flex", flexWrap: "wrap", justifyContent: "flex-start" }}>
        {activities.length > 0 && activities?.map((act: any, key: number) => {
          return <UserActivity activity={act} key={key}  handleClickDeleteActivity={handleClickDeleteActivity}/>
        })}
        {!activities.length && <Typography gutterBottom sx={{ paddingLeft: 2, paddingTop: 2, color: 'text.secondary', fontSize: 14 }}>
          No Activity Available
        </Typography>}
      </Box>
    </>
  )
}

export default UserActivityList