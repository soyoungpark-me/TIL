import { Todo } from './index'

export const ADD_TODO = 'ADD_TODO'
export const CHECK_TODO = 'CHECK_TODO'
export const REMOVE_TODO = 'REMOVE_TODO'

type State = {
  todoList: Todo[]
}

type Action = 
  | { type: 'ADD_TODO'; text: string}
  | { type: 'CHECK_TODO'; index: number }
  | { type: 'REMOVE_TODO'; index: number }

export default function reducer(state: State, action: Action): State {
  const { todoList } = state

  switch (action.type) {
    case ADD_TODO:
      if ( action.text == null || action.text.length === 0) return state
      const newTodo: Todo = {
        index: todoList.length,
        text: action.text,
        checked: false
      }
      return { todoList: [...todoList, newTodo] }
    case CHECK_TODO:
      const target = todoList[action.index]
      const checkedValue: boolean = target.checked
      target.checked = !checkedValue

      return { todoList: [
        ...todoList.slice(0, action.index),
        target,
        ...todoList.slice(action.index + 1)
      ]}
    case REMOVE_TODO:
      const afterList = todoList.slice(action.index + 1)
      afterList.map((item) => { item.index-- })
      const newTodoList = todoList.slice(0, action.index).concat(afterList)
      return { todoList: newTodoList }
    default:
      return state
  }
}