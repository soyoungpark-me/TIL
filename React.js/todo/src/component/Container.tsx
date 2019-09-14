import React, { useState } from 'react'

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

  return (
    <TodoContext.Provider value={{ todoList }}>
      <Header addNewTodo={addNewTodo} />
      <Todo checkTodo={checkTodo} removeTodo={removeTodo} />
    </TodoContext.Provider>
  )
}

export default Container