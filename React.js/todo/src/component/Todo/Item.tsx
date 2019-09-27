import React, { useContext, useCallback } from 'react'
import { Col, Card, Checkbox, Button } from 'antd'

import { Todo, TodoContext } from './../../Store'
import { CHECK_TODO, REMOVE_TODO } from './../../Store/reducer'
import './Todo.css'

interface Props {
  item: Todo
}

const Item = (props: Props) => {
  const { dispatch } = useContext(TodoContext)
  const { item } = props
  const { index, text, checked } = item
  const checkTodo = useCallback(() => dispatch({ type: CHECK_TODO, index }), [index])
  const removeTodo = useCallback(() => dispatch({ type: REMOVE_TODO, index }), [index])
 
  return (
    <>
      <Col span={24}>
        <Card bordered={true} className={`Item-card ${checked && 'Item-checked'}`}>
          <Checkbox className={`Item-check-btn ${checked && 'Item-checked-checkbox'}`} onChange={checkTodo}>{text}</Checkbox>
          <Button className="Item-remove-btn" icon="delete" shape="circle" size="small" onClick={removeTodo} />
        </Card>
      </Col>
    </>
  )
}

export default Item