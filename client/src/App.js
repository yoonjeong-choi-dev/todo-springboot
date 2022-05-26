import React, { useState, useEffect, useCallback } from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Typography, Container, Box, Grid, Button, AppBar, Toolbar } from '@material-ui/core';
import { EmailOutlined, Email, Info, InfoOutlined } from '@material-ui/icons';

import { getLoginStatus, logout } from './shared/storage';

import WelcomePage from './page/WelcomPage';
import TodoPage from './page/todo/TodoPage';
import LoginPage from './page/user/LoginPage';
import SignUpPage from './page/user/SignUpPage';
import UserDetailPage from './page/user/UserDetailPage';

const Header = (props) => {

  const isLogin = props.isLogin;
  const setLoginState = props.setLoginState;

  const onClickLogo = useCallback(()=> {
    window.location.href = '/';
  })

  const onClickLoginButton = useCallback(() => {
    window.location.href = '/login';
  });

  const onClickLogoutButton = useCallback(() => {
    setLoginState(false);
    logout();
  });

  const onClickSignUpButton = useCallback(()=> {
    window.location.href = '/signup';
  })

  const onClickDetailButton = useCallback(()=> {
    window.location.href = '/userdetail'
  })

  const logoutButtonGrid = (
    <Grid item>
      <Button color='inherit' onClick={onClickDetailButton}>
        유저 정보
      </Button>
      <Button color='inherit' onClick={onClickLogoutButton}>
        로그아웃
      </Button>
    </Grid>
  );

  const loginButtonGrid = (
    <Grid item>
      <Button color='inherit' onClick={onClickLoginButton}>
        로그인
      </Button>
      <Button color='inherit' onClick={onClickSignUpButton}>
        회원가입
      </Button>
    </Grid>
  );

  return (
    <AppBar position='static'>
      <Toolbar>
        <Grid justifyContent='space-between' container>
          <Grid item onClick={onClickLogo} style={{cursor: 'pointer'}}>
            <Typography variant='h5'>YJ Todo App!</Typography>
          </Grid>

          {isLogin ? logoutButtonGrid : loginButtonGrid}

        </Grid>
      </Toolbar>
    </AppBar>
  );
}


const Footer = () => {

  return (
    <Container>
      <Typography variant='body2' color='textSecondary' align='center' style={{ display: 'inline-flex', verticalAlign: "middle" }}>
        <Info color='primary' style={{ paddingRight: 5 }}>
          <InfoOutlined />
        </Info>

        <a href='https://github.com/yoonjeong-choi-dev'>yoonjeong-choi-dev</a>, {new Date().getFullYear()}
      </Typography>

      <br />

      <Typography variant='body2' color='textSecondary' align='center' style={{ display: 'inline-flex', verticalAlign: "middle" }}>
        <Email color='primary' style={{ paddingRight: 5 }}>
          <EmailOutlined />
        </Email>
        <a href='mailto:yjchoi7166@gmail.com'>yjchoi7166@gmail.com</a>
      </Typography>
    </Container>

  )
}

function App() {

  const [loginState, setLoginState] = useState(false);

  // Fetch todo data
  useEffect(() => {
    const currentState = getLoginStatus();
    setLoginState(currentState);
  }, []);

  return (
    <div style={{textAlign : 'center'}}>
      <Header isLogin={loginState} setLoginState={setLoginState} />

      <Router>
        <Routes>
          <Route exact path='/' element={<TodoPage setLoginState={setLoginState}/>} />
          <Route exact path='/welcome' element={<WelcomePage />} />
          <Route exact path='/login' element={<LoginPage setLoginState={setLoginState} />} />
          <Route exact path='/signup' element={<SignUpPage />} />
          <Route exact path='/userdetail' element={<UserDetailPage setLoginState={setLoginState}/>} />
        </Routes>
      </Router>

      <Box mt={5}>
        <Footer />
      </Box>

    </div>
  );
}

export default App;
