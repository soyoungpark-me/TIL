import React from 'react'
import { Input, Button, Icon } from 'antd'

import './Header.css';

const Header = () => {
  return (
    <>
      <div className="Header-wrapper">
        <div className="Header">
          <h1 className="Header-title">
            To-Do List
          </h1>
          <div className="Header-input-wrapper">
            <Input className="Header-input"/>
            <Button className="Header-button" type="primary" size="large">
              <Icon type="plus" />ADD
            </Button>
          </div>
        </div>
      </div>
    </>
  )
}

export default Header