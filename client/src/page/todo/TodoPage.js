import React, { useState, useEffect } from 'react';
import { Paper, List, Container } from '@material-ui/core';

import { get, post, update, del } from '../../shared/apis';
import { logout } from '../../shared/storage';

import Todo from '../../container/todo/Todo';
import TodoCreate from '../../container/todo/TodoCreate';
import TodoHeader from '../../container/todo/TodoHeader';

const TodoPage = (props) => {
  const setLoginState = props.setLoginState;

  const [state, setState] = useState({
    items: [],
    loading: true,
  });

  // Fetch todo data
  useEffect(() => {
    get('/todo')
      .then(res => res.data)
      .then((res) => {
        setState({ items: res, loading: false });
      })
      .catch(err => {
        if (err.response && err.response.data.status === 401) {
          // 토큰이 없거나, 잘못된 토큰 => logout 이용해서 토큰 및 유저 아이디 데이터 삭제
          setLoginState(false);
          logout();
        } else {
          console.error(err);
        }
      });
  }, []);

  const addTodo = (content) => {
    post('/todo', { content })
      .then(res => res.data)
      .then((res) => {
        console.log(res);
        const newItem = {
          id: res.id,
          content,
          completed: false,
        };
        const items = [...state.items, newItem];
        setState({ ...state, items, });
      })
      .catch(err => {
        console.error(err);
      });
  };

  const deleteTodo = (item) => {
    del(`/todo/${item.id}`, null)
      .then((res) => {
        const items = state.items.filter(todo => todo.id !== item.id);
        setState({ ...state, items });
      })
      .catch(err => {
        console.error(err);
      });
  };

  const updateTodo = (item) => {
    update(`/todo/${item.id}`, item)
      .then((res) => {
        const items = state.items.map(todo => todo.id === item.id ? item : todo);
        setState({ ...state, items });
      })
      .catch(err => {
        console.error(err);
      });
  };

  const todoItems = state.items.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {state.items.map((item) => {
          return <Todo
            item={item}
            key={item.id}
            delete={deleteTodo}
            update={updateTodo}
          />;
        })}
      </List>
    </Paper>
  );



  const todoDiv = (
    <Container maxWidth='md'>
      <TodoHeader />
      <TodoCreate add={addTodo} />
      <div className='TodoList'>{todoItems}</div>
    </Container>
  );

  const loadingDiv = <h1>Loading...</h1>;

  return (
    <div style={{ paddingTop: 40, textAlign: 'center' }}>
      {state.loading ? loadingDiv : todoDiv}
    </div>
  );
}

export default TodoPage;