import React, { useState } from "react";
import { ListItem, ListItemText, ListItemSecondaryAction, InputBase, Checkbox, IconButton } from "@material-ui/core";
import { DeleteOutlined } from "@material-ui/icons";


const Todo = (props) => {
  const [state, setState] = useState({ item: props.item, readOnly: true, editStyle: {} });

  const updateItem = props.update;
  const deleteItem = props.delete;

  const onClickCheckBox = () => {
    const item = state.item;
    item.completed = !item.completed;
    setState({
      ...state,
      item,
    });

    updateItem(item);
  };

  const onClickToEdit = () => {
    setState({
      ...state,
      readOnly: false,
      editStyle: { border: '1px solid black',},
    });
  };

  const updateInput = () => {
    setState({
      ...state,
      readOnly: true,
      editStyle: {},
    });

    updateItem(state.item);
  }

  const onPressKeyEnter = (e) => {
    if (e.key === 'Enter') {
      updateInput();
    }
  };

  const onEditTodoTextInput = (e) => {
    const item = state.item;
    item.content = e.target.value;

    setState({
      ...state,
      item,
    });
  };

  const onClickDelete = (e) => {
    deleteItem(state.item);
  };

  return (
    <ListItem>
      <Checkbox color="primary" checked={state.item.completed} onChange={onClickCheckBox} />

      <ListItemText style={{paddingRight: '8px',}}>
        <InputBase
          inputProps={{
            "aria-label": "naked",
            readOnly: state.readOnly,
          }}
          style={state.editStyle}
          type='text'
          id={state.item.id}
          name={state.item.name}
          value={state.item.content}
          multiline={true}
          fullWidth={true}
          onClick={onClickToEdit}
          onKeyPress={onPressKeyEnter}
          onChange={onEditTodoTextInput}
          onBlur={updateInput}
        />
      </ListItemText>


      <ListItemSecondaryAction>
        <IconButton aria-label="Delete Todo" onClick={onClickDelete}>
          <DeleteOutlined />
        </IconButton>
      </ListItemSecondaryAction>

    </ListItem>
  );

};

export default Todo;