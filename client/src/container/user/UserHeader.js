import React from "react";
import { Typography, AppBar, Toolbar } from '@material-ui/core';

const UserHeader = (props) => {

  return (
    <AppBar position="static" color="primary" style={{ marginBottom: 16, width: 'auto' }}>
      <Toolbar style={{ paddingLeft: 12 }}>
        <Typography variant="h5">
          {props.title}
        </Typography>

      </Toolbar>
    </AppBar>
  );
}

export default UserHeader;