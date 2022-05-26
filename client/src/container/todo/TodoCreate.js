import React, { useState } from "react";
import { TextField, Paper, Button, Grid } from "@material-ui/core";

const TodoCreate = (props) => {
  const [content, setContent] = useState("");
  const add = props.add;

  const onClickAddButton = () => {
    add(content);
    setContent("");
  };

  const onInputChange = (e) => {
    setContent(e.target.value);
  };

  const onPressKeyEnter = (e) => {
    if(e.key === 'Enter') {
      onClickAddButton();
    }
  };

  return (
    <Paper style={{ margin: 16, padding: 16 }}>
      <Grid container>
        <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
          <TextField
            placeholder='Create Todo !'
            fullWidth
            onChange={onInputChange}
            onKeyPress={onPressKeyEnter}
            value={content}
          />
        </Grid>

        <Grid xs={1} md={1} item>
          <Button fullWidth color='primary' variant="outlined" onClick={onClickAddButton}>
            +
          </Button>
        </Grid>

      </Grid>
    </Paper>
  )

};

export default TodoCreate;