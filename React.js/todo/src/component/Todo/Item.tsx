import React, { useState } from 'react'
import { Col, Card, Checkbox, Button } from 'antd'

import './Todo.css'

interface ItemProps {
  text: string
}

const Item = (props: ItemProps) => {
  const [checked, setChecked] = useState(true)

  return (
    <>
      <Col span={24}>
        <Card bordered={true} className="Item-card">
          <Checkbox className={`Item-check-btn ${checked && 'Item-checked'}`}>{props.text}</Checkbox>
          <Button className="Item-remove-btn" icon="delete" shape="circle" size="small" />
        </Card>
      </Col>
    </>
  )
}

export default Item