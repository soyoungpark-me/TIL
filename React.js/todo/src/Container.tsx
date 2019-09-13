import React, { useState } from 'react';

import { Checkbox } from 'antd';

const CheckboxGroup = Checkbox.Group;

const plainOptions = ['Apple', 'Pear', 'Orange'];
const defaultCheckedList = ['Apple', 'Orange'];


export default function Container() {
  const [state, setState] = useState({
    checkedList: defaultCheckedList,
    indeterminate: true,
    checkAll: false
  });

  const onChange = (checkedList: any) => {
    setState({
      checkedList,
      indeterminate: !!checkedList.length && checkedList.length < plainOptions.length,
      checkAll: checkedList.length === plainOptions.length,
    });
  };

  const onCheckAllChange = (e: any) => {
    setState({
      checkedList: e.target.checked ? plainOptions : [],
      indeterminate: false,
      checkAll: e.target.checked,
    });
  };

  return (
    <div>
      이거 해야 퇴근함
      <div style={{ borderBottom: '1px solid #E9E9E9' }}>
        <Checkbox
          indeterminate={state.indeterminate}
          onChange={onCheckAllChange}
          checked={state.checkAll}
        >
          Check all
        </Checkbox>
      </div>
      <br />
      <CheckboxGroup
        options={plainOptions}
        value={state.checkedList}
        onChange={onChange}
      />
    </div>
  )
}