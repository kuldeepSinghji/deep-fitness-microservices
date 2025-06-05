import { Container, Typography, Box, Button, ThemeProvider, createTheme } from '@mui/material';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import { Link } from 'react-router';

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
      fontSize: '3rem',
      fontWeight: 700,
      marginBottom: '1rem',
      textAlign: 'center',
    },
    body1: {
      fontSize: '1.2rem',
      marginBottom: '2rem',
      textAlign: 'center',
    },
  },
});

const NotFound = () => {

    return (
    <ThemeProvider theme={theme}>
        <Container maxWidth="md" sx={{my:10}}>
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              textAlign: 'center',
            }}
          >
            <ErrorOutlineIcon color="secondary" sx={{ fontSize: 100, mb: 2 }} />
            <Typography variant="h1" color="primary">
              404 - Page Not Found
            </Typography>
            <Typography variant="body1" color="textSecondary">
              Oops! It looks like the page you're looking for doesn't exist on d33pFitness.
            </Typography>
            <Button
              variant="contained"
              color="primary"
              size="large"
              component={Link}
              to="/"
              sx={{
                mt: 3,
                fontSize: '1.1rem',
                padding: '10px 20px',
                borderRadius: '8px',
              }}
            >
              Back to Homepage
            </Button>
          </Box>
        </Container>
    </ThemeProvider>
  );
};

export default NotFound;