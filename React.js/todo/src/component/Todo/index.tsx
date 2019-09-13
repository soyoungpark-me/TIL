import React from 'react'
import { Row } from 'antd'

import Item from './Item'
import './Todo.css'

const Todo = () => {
  return (
    <>
      <div className="Todo-wrapper">
        <h2 className="Todo-header">
          {/* 다 해야 퇴근 가능^_^ */}
        </h2>
        {/* <div style={{ background: '#ECECEC', padding: '30px' }}> */}
        <div>
          <Row gutter={24}>
            <Item text={'집에 가기'} />
            <Item text={'집에 가기'} />
          </Row>
        </div>
      </div>
    </>
  )
}

export default Todo