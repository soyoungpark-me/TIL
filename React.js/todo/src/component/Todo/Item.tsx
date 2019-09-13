import React, { useState } from 'react'
import { Col, Card, Checkbox, Button } from 'antd'

import { todo } from './../Container'
import './Todo.css'


const Item = (props: todo) => {
  const { text, checked } = props

  return (
    <>
      <Col span={24}>
        <Card bordered={true} className={`Item-card ${checked && 'Item-checked'}`}>
          <Checkbox className={`Item-check-btn ${checked && 'Item-checked-checkbox'}`}>{text}</Checkbox>
          <Button className="Item-remove-btn" icon="delete" shape="circle" size="small" />
        </Card>
      </Col>
    </>
  )
}

export default Item