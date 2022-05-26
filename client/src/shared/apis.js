import axios from 'axios';
import { API_BASE_URL } from "./config";
import { getAuthToken } from './storage';

function createAuthHeader() {
  const headers = {
    'Content-Type': 'application/json',
  };

  // API 호출을 위한 토큰을 로컬 스토리지에서 가져오기
  const token = getAuthToken();

  // 토큰을 헤더에 추가
  if (token && token !== null) {
    headers['Authorization'] = 'Bearer ' + token;
  }

  return headers;
}

export function get(path, params) {
  const headers = createAuthHeader();
  return axios({
    method: 'GET',
    url: path.startsWith('http') ? path : (API_BASE_URL + path),
    headers,
    data: params,
  });
}

export function post(path, params) {
  const headers = createAuthHeader();

  return axios({
    method: 'POST',
    url: path.startsWith('http') ? path : (API_BASE_URL + path),
    headers,
    data: params,
  });
};

export function update(path, params) {
  const headers = createAuthHeader();

  return axios({
    method: 'PUT',
    url: path.startsWith('http') ? path : (API_BASE_URL + path),
    headers,
    data: params,
  });
};

export function del(path, params) {
  const headers = createAuthHeader();

  return axios({
    method: 'DELETE',
    url: path.startsWith('http') ? path : (API_BASE_URL + path),
    headers,
    data: params,
  });
};
