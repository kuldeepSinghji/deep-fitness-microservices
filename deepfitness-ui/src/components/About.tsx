
import { Container, Typography, Box, Grid, Paper, Divider } from '@mui/material';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2', 
    },
    secondary: {
      main: '#f50057', 
    },
    background: {
      default: '#f4f6f8',
    },
  },
  typography: {
    h1: {
      fontSize: '2.5rem',
      fontWeight: 700,
      marginBottom: '1rem',
    },
    h5: {
      fontWeight: 500,
      marginBottom: '1rem',
    },
    body1: {
      fontSize: '1.1rem',
      lineHeight: 1.6,
    },
  },
});

const About = () => {
  return (
    <ThemeProvider theme={theme}>
        <Container maxWidth="lg">
          <Grid container spacing={4} justifyContent="center">
              <Paper elevation={3} sx={{ p: 4, borderRadius: 2 }}>
                <Box display="flex" alignItems="center" mb={3}>
                  <FitnessCenterIcon color="primary" sx={{ fontSize: 40, mr: 2 }} />
                  <Typography variant="h1" color="primary">
                    About D33pFitness
                  </Typography>
                </Box>
                <Typography variant="body1" paragraph>
                  D33pFitness is your ultimate companion for achieving your fitness goals. Our application is designed to provide personalized workout plans, nutrition tracking, and progress monitoring to help you lead a healthier lifestyle. Whether you're a beginner or a seasoned athlete, D33pFitnes offers tools and insights to elevate your fitness journey.
                </Typography>
                <Typography variant="body1" paragraph>
                  Built with cutting-edge technology, D33pFitnes leverages data-driven insights to tailor recommendations to your unique needs. Our intuitive interface ensures that you can focus on what matters most—your health and well-being.
                </Typography>
                <Divider sx={{ my: 4 }} />
                <Typography variant="h5" color="secondary">
                  Our Mission
                </Typography>
                <Typography variant="body1" paragraph>
                  At D33pFitnes, our mission is to empower individuals to take control of their fitness journey through accessible, innovative, and science-backed solutions. We believe that everyone deserves the opportunity to live a healthier, happier life, and we're here to make that a reality.
                </Typography>
                <Typography variant="body1" paragraph>
                  We are committed to fostering a community of fitness enthusiasts who inspire and support each other. With D33pFitnes, you're not just working out—you're joining a movement to redefine personal wellness.
                </Typography>
              </Paper>
          </Grid>
        </Container>
    </ThemeProvider>
  );
};

export default About;