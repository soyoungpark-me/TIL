import React, { createContext, useState } from 'react'

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
    console.log(todoList)
  }

  const checkTodo = (index: number) => {
    const items: todo[] = todoList
    const checkedValue: boolean = items[index].checked
    items[index].checked = !checkedValue

    setTodoList(items)
  }

  return (
    <TodoContext.Provider value={{ todoList }}>
      <Header addNewTodo={addNewTodo} />
      <Todo checkTodo={checkTodo} />
    </TodoContext.Provider>
  )
}

export default Container