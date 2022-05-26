import React from "react";
import { Container } from '@material-ui/core';

const WelcomePage = () => {
  return (
    <div style={{ paddingTop: 40, textAlign: 'center' }}>
      <Container maxWidth='md'>
        <h2>Welcome! This is Simple Todo Service with Spring boot!</h2>
        You need to login to use!
      </Container>
    </div>
  )
};

export default WelcomePage;