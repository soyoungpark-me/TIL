import React from 'react'

export interface Todo {
  index: number
  text: string
  checked: boolean
}

interface TodoContextInterfece {
  todoList: Todo[]
  dispatch: any
}

export const TodoContext = React.createContext<TodoContextInterfece>({
  todoList: [],
  dispatch: null
});