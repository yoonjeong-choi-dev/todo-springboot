import React from "react";
import { Typography, AppBar, Toolbar } from '@material-ui/core';

const TodoHeader = (props) => {

  const date = new Date();
  const dateString = date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  });
  const dayName = date.toLocaleDateString('ko-KR', { weekday: 'long' });

  return (
    <AppBar position="static" color="primary" style={{ margin: 16, padding: 8, width: 'auto' }}>
      <Toolbar>
        <Typography variant="h5">
          {dateString}

          <Typography variant="body2" style={{ color: "#e9ecef", textAlign: 'left' }}>
            {dayName}
          </Typography>

        </Typography>

      </Toolbar>
    </AppBar>
  );
}

export default TodoHeader;