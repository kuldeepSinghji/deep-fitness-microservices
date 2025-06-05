import { Container, Typography, Box, Button, ThemeProvider, createTheme } from '@mui/material';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2', // d33pFitness brand color (blue)
    },
    secondary: {
      main: '#f50057', // Accent color (pink)
    },
    background: {
      default: '#f4f6f8',
    },
  },
  typography: {
    h1: {
      fontSize: '3rem',
      fontWeight: 700,
      marginBottom: '2rem',
      textAlign: 'center',
    },
  },
});

const MainPage = ({logIn}:any) => {
  const handleButtonClick = () => {
    logIn();
  };

  return (
    <ThemeProvider theme={theme}>
        <Container maxWidth="md">
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              textAlign: 'center',
              my:10
            }}
          >
            <FitnessCenterIcon color="primary" sx={{ fontSize: 60, mb: 2 }} />
            <Typography variant="h1" color="primary">
              Welcome to d33pFitness
            </Typography>
            <Button
              variant="contained"
              color="secondary"
              size="large"
              onClick={handleButtonClick}
              startIcon={<FitnessCenterIcon />}
              sx={{
                fontSize: '1.2rem',
                padding: '12px 24px',
                borderRadius: '8px',
              }}
            >
              Click to Login or Register on d33pFitness
            </Button>
          </Box>
        </Container>
    </ThemeProvider>
  );
};

export default MainPage;