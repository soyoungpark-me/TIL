import React, { createContext, useState } from 'react'

import Header from './Header'
import Todo from './Todo'
import './../index.css'

enum Label {
  IMPORTANT, SOSO
}

export interface todo {
  index?: number
  text: string
  checked: boolean
  labal?: Label
}

interface TodoContextInterfece {
  todoList: todo[]
}

export const TodoContext = React.createContext<TodoContextInterfece>({
  todoList: []
});

const Container = () => {
  const [todoList, setTodoList] = useState<todo[]>([])

  const addNewTodo = (text: string) => {
    const newTodo: todo = {
      index: todoList.length,
      text,
      checked: false
    }
    setTodoList([...todoList, newTodo])
  }

  return (
    <TodoContext.Provider value={{ todoList }}>
      <Header addNewTodo={addNewTodo}/>
      <Todo />
    </TodoContext.Provider>
  )
}

export default Container