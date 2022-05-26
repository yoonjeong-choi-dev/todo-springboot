import React from "react";
import { Button, TextField, Link, Grid, Container } from "@material-ui/core";

import { post } from "../../shared/apis";

import UserHeader from "../../container/user/UserHeader";

const SignUpPage = (props) => {
  const onEventSubmit = (e) => {
    e.preventDefault();

    const data = new FormData(e.target);
    const id = data.get('id');
    const password = data.get('password');
    const email = data.get('email');
    const name = data.get('name');
    const description = data.get('description');

    if (!id || !password || !email) {
      alert("You must enter your id, password and email!");
      return;
    }


    post('/member/register', { id, password, email, name, description, })
      .then((res) => {
        // 가입 성공 시 로그인 페이지로 리다이렉트
        window.location.href = '/login';
      })
      .catch(err => {
        if (err.response && err.response.data.type === 'UserAlreadyExistException') {
          alert(err.response.data.message);
        } else {
          console.error(err);
        }
      });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: 40 }}>
      <UserHeader title='회원가입' />

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

          {/* email */}
          <Grid item xs={12}>
            <TextField variant="outlined" required fullWidth
              id="email" label="이메일" name="email" autoComplete="email" />
          </Grid>

          {/* name */}
          <Grid item xs={12}>
            <TextField variant="outlined" fullWidth
              id="name" label="이름" name="name" />
          </Grid>

          {/* description */}
          <Grid item xs={12}>
            <TextField variant="outlined" fullWidth multiline minRows={2}
              id="description" label="소개" name="description" />
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
        <Link href="/login" variant="body2">
          이미 계정이 있으십니까? 로그인하세요.
        </Link>
      </Grid>

    </Container>
  );

};

export default SignUpPage;