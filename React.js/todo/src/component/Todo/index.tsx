import React, { useContext } from 'react'
import { Row } from 'antd'

import { TodoContext } from './../Container'
import Item from './Item'
import './Todo.css'

/*
interface Props {
  checkTodo: Function
  removeTodo: Function
}
*/

const Todo = (/*props: Props*/) => {
  const todoList = useContext(TodoContext).todoList

  // const { checkTodo, removeTodo } = props

  return (
    <>
      <div className="Todo-wrapper">
        <h2 className="Todo-header">
        </h2>
        <div className="Todo-list-wrapper">
          <Row gutter={24}>
            {todoList && todoList.map(item => (
              <Item key={`item-${item.index}`} item={item} />
            ))}
          </Row>
        </div>
      </div>
    </>
  )
}

export default Todo