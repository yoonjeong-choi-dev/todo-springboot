import React, { useEffect, useState, useCallback } from "react";
import { Button, TextField, Link, Grid, Container } from "@material-ui/core";

import { get, update, del } from "../../shared/apis";
import { getUserId, logout } from "../../shared/storage";

import UserHeader from "../../container/user/UserHeader";

const UserDetailPage = (props) => {
  const setLoginState = props.setLoginState;

  const [user, setUser] = useState({});
  const [pageState, setPageState] = useState({ loading: true });

  const logoutUtil = useCallback(() => {
    setLoginState(false);
    logout();
  });


  // Fetch user data
  useEffect(() => {
    const userId = getUserId();
    get(`/member/${userId}`)
      .then(res => res.data)
      .then((res) => {
        console.log(res);
        setUser(res);
        setPageState({
          ...pageState,
          loading: false,
        });
      })
      .catch(err => {
        if (err.response && err.response.data.status === 401) {
          // 토큰이 없거나, 잘못된 토큰 => logout 이용해서 토큰 및 유저 아이디 데이터 삭제
          logoutUtil();
        } else {
          console.error(err);
        }
      });
  }, []);

  const onChangeForm = (e) => {
    const {name, value} = e.target;
    setUser({
      ...user,
      [name]: value,
    });
  }


  const onClickSubmitButton = (e) => {
    e.preventDefault();

    const {name, description} = user;

    update(`/member/${user.id}`, { name, description })
      .then(res => {
        alert('수정 완료');
      })
      .catch(err => {
        if (err.response && err.response.data.status === 401) {
          // 토큰이 없거나, 잘못된 토큰 => logout 이용해서 토큰 및 유저 아이디 데이터 삭제
          logout();
        } else {
          console.error(err);
        }
      });
  };

  const onClickWithdrawalButton = () => {
    let ret = window.confirm("정말 탈퇴 하시겠습니까?");
    if (!ret) return;

    del(`/member/${user.id}`, null)
      .then(res => {
          // 탈퇴하였으므로 로그아웃 처리
          alert("탈퇴 처리 완료")
          logoutUtil();
      }).catch(err => {
        if (err.response && err.response.data.status === 401) {
          // 토큰이 없거나, 잘못된 토큰 => logout 이용해서 토큰 및 유저 아이디 데이터 삭제
          logoutUtil();
        } else {
          console.error(err);
        }
      })
  }

  const loadingDiv = <h1>Loading...</h1>;

  const formDiv = (
    <form noValidate onSubmit={onClickSubmitButton} onChange={onChangeForm}>

      <Grid container spacing={2}>
        {/* id */}
        <Grid item xs={12}>
          <TextField variant="outlined" fullWidth disabled
            value={user.id} label="아이디" />
        </Grid>

        {/* email */}
        <Grid item xs={12}>
          <TextField variant="outlined" fullWidth disabled
            value={user.email} label="이메일" />
        </Grid>

        {/* name */}
        <Grid item xs={12}>
          <TextField variant="outlined" fullWidth
            value={user.name}
            id="name" label="이름" name="name" />
        </Grid>

        {/* description */}
        <Grid item xs={12}>
          <TextField variant="outlined" fullWidth multiline minRows={2}
            value={user.description}
            id="description" label="소개" name="description" />
        </Grid>

        {/* submit */}
        <Grid item xs={6}>
          <Button type="submit" fullWidth variant="contained" color="primary">
            수정하기
          </Button>
        </Grid>

        <Grid item xs={6}>
          <Button fullWidth variant="contained" color="primary" onClick={onClickWithdrawalButton}>
            탈퇴하기
          </Button>
        </Grid>

      </Grid>
    </form>

  )

  return (
    <Container component="main" maxWidth="md" style={{  marginTop: 40 }}>
      <UserHeader title='회원 정보' />
      {pageState.loading ? loadingDiv : formDiv}

      <Grid container justifyContent="flex-end" style={{ marginTop: 5 }}>
        <Link href="/" variant="body2">
          Todo List 페이지로 돌아가기
        </Link>
      </Grid>

    </Container>
  )
};

export default UserDetailPage;