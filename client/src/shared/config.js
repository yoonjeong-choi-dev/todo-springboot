const hostname = window && window.location && window.location.hostname;

let serverHost;
if(hostname === 'localhost') {
  serverHost = 'http://localhost:7166';
}

export const API_BASE_URL = `${serverHost}`;
export const ACCESS_TOEKN_LOCAL_STORAGE_KEY = 'ACCESS_TOKEN';
export const USER_ID_LOCAL_STORAGE_KEY = 'USER_ID';