import React, { useEffect } from 'react'
import { Col, Card, Checkbox, Button } from 'antd'

import { todo } from './../Container'
import './Todo.css'

interface Props {
  item: todo
  checkTodo: Function
}

const Item = (props: Props) => {
  const { item, checkTodo } = props
  const { index, text, checked } = item

  useEffect(() => {
    console.log(props)
  }, [checked])

  return (
    <>
      <Col span={24}>
        <Card bordered={true} className={`Item-card ${checked && 'Item-checked'}`}>
          <Checkbox className={`Item-check-btn ${checked && 'Item-checked-checkbox'}`} onChange={() => {checkTodo(index)}}>{text}</Checkbox>
          <Button className="Item-remove-btn" icon="delete" shape="circle" size="small" />
        </Card>
      </Col>
    </>
  )
}

export default Item