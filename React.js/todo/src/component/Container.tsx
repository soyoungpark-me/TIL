import React, { useState, useReducer } from 'react'

import Header from './Header'
import Todo from './Todo'
import './../index.css'

enum Label {
  IMPORTANT, SOSO
}

export interface todo {
  index: number
  text: string
  checked: boolean
  labal?: Label
}

interface TodoContextInterfece {
  todoList: todo[]
  dispatch: any
}

export const TodoContext = React.createContext<TodoContextInterfece>({
  todoList: [],
  dispatch: null
});

export const ADD_TODO = 'ADD_TODO'
export const CHECK_TODO = 'CHECK_TODO'
export const REMOVE_TODO = 'REMOVE_TODO'

type State = {
  todoList: todo[]
}

type Action = 
  | { type: 'ADD_TODO'; text: string}
  | { type: 'CHECK_TODO'; index: number }
  | { type: 'REMOVE_TODO'; index: number }

function reducer(state: State, action: Action): State {
  switch (action.type) {
    case ADD_TODO:
      if ( action.text == null || action.text.length === 0) return state
      const newTodo: todo = {
        index: state.todoList.length,
        text: action.text,
        checked: false
      }
      return { todoList: [...state.todoList, newTodo] }
    default:
      return state
  }
}
const Container = () => {
  // const [todoList, setTodoList] = useState<todo[]>([])
  const [state, dispatch] = useReducer(reducer, { todoList: [] });

  /*
  const addNewTodo = (text: string) => {
    if (text === null || text.length === 0) return

    const newTodo: todo = {
      index: todoList.length,
      text,
      checked: false
    }
    setTodoList([...todoList, newTodo])
  }

  const checkTodo = (index: number) => {
    const target = todoList[index]
    const checkedValue: boolean = target.checked
    target.checked = !checkedValue

    setTodoList([
      ...todoList.slice(0, index),
      target,
      ...todoList.slice(index + 1)
    ])
  }

  const removeTodo = (index: number) => {
    const afterList = todoList.slice(index + 1)
    afterList.map((item) => { item.index-- })
    const newTodoList = todoList.slice(0, index).concat(afterList)
    
    setTodoList(newTodoList)
  }
  */

  return (
    <TodoContext.Provider value={{ todoList: state.todoList, dispatch }}>
      <Header />
      <Todo />
    </TodoContext.Provider>
  )
}

export default Container