import React, { useContext } from 'react'
import { Row } from 'antd'

import { TodoContext } from './../Container'
import Item from './Item'
import './Todo.css'

const Todo = () => {
  const { todoList } = useContext(TodoContext)

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