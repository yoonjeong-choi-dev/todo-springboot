import React from "react";
import { Container, Button, TextField, Grid, Link } from "@material-ui/core";

import { post } from "../../shared/apis";
import { saveAuthToken, saveUserId } from "../../shared/storage";

import UserHeader from "../../container/user/UserHeader";


const LoginPage = (props) => {
  const setLoginState = props.setLoginState;

  const onEventSubmit = (e) => {
    e.preventDefault();

    const data = new FormData(e.target);
    const id = data.get('id');
    const password = data.get('password');

    if (!id || !password) {
      alert("You must enter your id and password!");
    } else {
      post('/auth/login', {id, password})
        .then(res => res.data)
        .then(res => {
          saveAuthToken(res.token);
          saveUserId(id);
          setLoginState(true);

          window.location.href = '/';
        })
        .catch(err => {
          if (err.response) {
            const data = err.response.data;
            if(data.type === 'ContentNotFoundException') {
              alert("There is no user : " + id);
            } else if(data.type === 'UserAuthenticationException') {
              alert("Password unmatched");
            }
          } else {
            console.error(err);
          }
        });
    }
  }

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: 40 }}>

      <UserHeader title='로그인'/>

      <form noValidate onSubmit={onEventSubmit}>

        <Grid container spacing={2}>
          {/* id */}
          <Grid item xs={12}>
            <TextField variant="outlined" required fullWidth
              id="id" label="아이디" name="id" autoComplete="id" />
          </Grid>

          {/* password */}
          <Grid item xs={12}>
            <TextField type="password" variant="outlined" required fullWidth
              id="password" label="비밀번호" name="password" autoComplete="current-password" />
          </Grid>

          {/* submit */}
          <Grid item xs={12}>
            <Button type="submit" fullWidth variant="contained" color="primary">
              로그인
            </Button>
          </Grid>

        </Grid>
      </form>

      <Grid container justifyContent="flex-end" style={{ marginTop: 5 }}>
        <Link href="/signup" variant="body2">
          <Grid item>계정이 없으시면, 회원가입을 하세요!</Grid>
        </Link>
      </Grid>
    </Container>
  )
};

export default LoginPage;