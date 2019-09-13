import React, { useState } from 'react'
import { Input, Button, Icon } from 'antd'

import './Header.css';

enum Day {
  SUN, MON, TUE, WED, THU, FRI, SAT
}

const today = new Date()
const todayString = `${today.getFullYear()}.${today.getMonth() + 1}.${today.getDate()} ${Day[today.getDay()]}`

interface Props {
  addNewTodo: Function
}

const Header = (props: Props) => {
  const [text, setText] = useState<string>("")

  return (
    <>
      <div className="Header-wrapper">
        <h1 className="Header-title">
          {todayString}
        </h1>
        <div className="Header-input-wrapper">
          <Input className="Header-input" value={text} onChange={e => setText(e.target.value)} />
          <Button className="Header-button" size="large" onClick={() => { props.addNewTodo(text); setText("")} }>
            <Icon type="plus" />ADD
          </Button>
        </div>
      </div>
    </>
  )
}

export default Header