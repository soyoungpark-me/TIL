import React, { useReducer } from 'react'

import Header from './Header'
import Todo from './Todo'
import './../index.css'

import { TodoContext } from './../Store'
import reducer from './../Store/reducer'

const Container = () => {
  const [state, dispatch] = useReducer(reducer, { todoList: [] });

  return (
    <TodoContext.Provider value={{ todoList: state.todoList, dispatch }}>
      <Header />
      <Todo />
    </TodoContext.Provider>
  )
}

export default Container