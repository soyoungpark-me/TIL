import React, { useState, useContext, useCallback } from 'react'
import { Input, Button, Icon } from 'antd'

import { TodoContext, ADD_TODO } from './../Container'
import './Header.css';

enum Day {
  SUN, MON, TUE, WED, THU, FRI, SAT
}

const today = new Date()
const todayString = `${today.getFullYear()}.${today.getMonth() + 1}.${today.getDate()} ${Day[today.getDay()]}`

const Header = () => {
  const context = useContext(TodoContext)
  const [text, setText] = useState<string>("")
  const addTodo = useCallback(() => { context.dispatch({ type: ADD_TODO, text }); setText("") }, [text])

  return (
    <>
      <div className="Header-wrapper">
        <h1 className="Header-title">
          {todayString}
        </h1>
        <div className="Header-input-wrapper">
          <Input className="Header-input" value={text} onChange={e => setText(e.target.value)} />
          <Button className="Header-button" size="large" onClick={addTodo/* () => { props.addNewTodo(text); setText("")} */}>
            <Icon type="plus" />ADD
          </Button>
        </div>
      </div>
    </>
  )
}

export default Header