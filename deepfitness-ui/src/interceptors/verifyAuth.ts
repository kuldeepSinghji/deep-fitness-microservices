import axios from "axios";


const axiosHttp = axios.create({
  baseURL: `http://localhost:8888/api`,
});

axiosHttp.interceptors.request.use(
  (config: any) => {
    const token = localStorage.getItem('token');
    return {
      ...config,
      headers: {
        ...(token !== null && { Authorization: `Bearer ${token}` }),
        'X-User-ID': localStorage.getItem('userId'),
        ...config.headers,
      },
    };
  },
  (error) => {
    // Safely check for error.response and error.response.status
    if (error.response && error.response.status === 401) {
      // handle 401 if needed
    }
    return Promise.reject(error);
  }
);

axiosHttp.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // handle 401 if needed
    }
    return Promise.reject(error);
  }
);

export default axiosHttp;
