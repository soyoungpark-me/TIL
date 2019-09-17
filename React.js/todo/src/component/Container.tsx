import React, { useReducer } from 'react'

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
  const { todoList } = state

  switch (action.type) {
    case ADD_TODO:
      if ( action.text == null || action.text.length === 0) return state
      const newTodo: todo = {
        index: todoList.length,
        text: action.text,
        checked: false
      }
      return { todoList: [...todoList, newTodo] }
    case CHECK_TODO:
      const target = todoList[action.index]
      const checkedValue: boolean = target.checked
      target.checked = !checkedValue

      return { todoList: [
        ...todoList.slice(0, action.index),
        target,
        ...todoList.slice(action.index + 1)
      ]}
    case REMOVE_TODO:
      const afterList = todoList.slice(action.index + 1)
      afterList.map((item) => { item.index-- })
      const newTodoList = todoList.slice(0, action.index).concat(afterList)
      return { todoList: newTodoList }
    default:
      return state
  }
}
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