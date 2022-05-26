import { ACCESS_TOEKN_LOCAL_STORAGE_KEY, USER_ID_LOCAL_STORAGE_KEY } from "./config";


export function saveAuthToken(token) {
  // 로컬 스토리지(브라우저 스토리지)에 토큰 저장 => 이후 토큰이 유효한 경우, 로그인 과정 필요 X
  localStorage.setItem(ACCESS_TOEKN_LOCAL_STORAGE_KEY, token);
}

export function getAuthToken() {
  return localStorage.getItem(ACCESS_TOEKN_LOCAL_STORAGE_KEY);
}

export function logout() {
  localStorage.removeItem(ACCESS_TOEKN_LOCAL_STORAGE_KEY);
  localStorage.removeItem(USER_ID_LOCAL_STORAGE_KEY);
  window.location.href = '/welcome';
}

export function getLoginStatus() {
  const token = localStorage.getItem(ACCESS_TOEKN_LOCAL_STORAGE_KEY);

  return token && token !== null;
}

export function saveUserId(userId) {
  localStorage.setItem(USER_ID_LOCAL_STORAGE_KEY, userId);
}

export function getUserId() {
  return localStorage.getItem(USER_ID_LOCAL_STORAGE_KEY);
}