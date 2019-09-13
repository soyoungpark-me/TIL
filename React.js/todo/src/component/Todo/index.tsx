import React, { useContext } from 'react'
import { Row } from 'antd'

import { TodoContext } from './../Container'
import Item from './Item'
import './Todo.css'

const Todo = () => {
  const todoList = useContext(TodoContext).todoList

  return (
    <>
      <div className="Todo-wrapper">
        <h2 className="Todo-header">
          {/* 다 해야 퇴근 가능^_^ */}
        </h2>
        {/* <div style={{ background: '#ECECEC', padding: '30px' }}> */}
        <div className="Todo-list-wrapper">
          <Row gutter={24}>
            {todoList && todoList.map(item => (
              <Item key={`item-${item.index}`} text={item.text} checked={item.checked} />
            ))}
          </Row>
        </div>
      </div>
    </>
  )
}

export default Todo