import { useState } from 'react';
import { Typography, Box, Paper, TextField, Button } from '@mui/material';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import ContactMailIcon from '@mui/icons-material/ContactMail';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2', // deepFitness brand color (blue)
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

const Contact = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    subject: '',
    message: '',
  });

  const handleChange = (e: any) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e: any) => {
    e.preventDefault();
    setFormData({ name: '', email: '', subject: '', message: '' });
  };

  return (
    <ThemeProvider theme={theme}>
      <Box maxWidth="lg" sx={{display:'flex',justifyContent:"center",minWidth:"95%",mx:2,flexWrap:"wrap"}}>
        <Paper elevation={3} sx={{ p: 4,m:2,borderRadius: 2 }}>
          <Box display="flex" alignItems="center" mb={3}>
            <ContactMailIcon color="primary" sx={{ fontSize: 40, mr: 2 }} />
            <Typography variant="h1" color="primary">
              Contact Us
            </Typography>
          </Box>
          <Typography variant="body1" paragraph>
            Have questions or need support? Reach out to the deepFitness team! We're here to help you with your fitness journey.
          </Typography>
          <Typography variant="h5" color="secondary">
            Get in Touch
          </Typography>
          <Typography variant="body1" paragraph>
            Email: support@deepfitness.com
          </Typography>
          <Typography variant="body1" paragraph>
            Phone: +1 (800) 555-1234
          </Typography>
          <Typography variant="body1" paragraph>
            Address: 123 Fitness Lane, Wellness City, FC 54321
          </Typography>
        </Paper>
        <Paper elevation={3} sx={{ p: 4,m:2 ,borderRadius: 2}}>
          <Typography variant="h5" color="secondary" mb={3}>
            Send Us a Message
          </Typography>
          <Box component="div" sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="Name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              variant="outlined"
              fullWidth
              required
            />
            <TextField
              label="Email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              variant="outlined"
              fullWidth
              required
            />
            <TextField
              label="Subject"
              name="subject"
              value={formData.subject}
              onChange={handleChange}
              variant="outlined"
              fullWidth
              required
            />
            <TextField
              label="Message"
              name="message"
              value={formData.message}
              onChange={handleChange}
              variant="outlined"
              multiline
              rows={4}
              fullWidth
              required
            />
            <Button
              variant="contained"
              color="primary"
              size="large"
              onClick={handleSubmit}
              sx={{ mt: 2 }}
            >
              Submit
            </Button>
          </Box>
        </Paper>
      </Box>
    </ThemeProvider>
  );
};

export default Contact;