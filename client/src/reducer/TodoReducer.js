const TodoReducer = (state, action) => {
    switch (action.type) {
        case "setTodoList":
            return action.payload;
        case "addTodo":
            return [ ...state, action.payload ];
        case "removeTodo":
            return removeTodo(state, action.payload);
        case "changeTodo":
            return changeTodo(state, action.payload);
        default:
            throw new Error("Unsupported action type");
    }
};

const removeTodo = (todoList, id) => {
    const index = todoList.findIndex(todo => todo.id === id);
    if (index < 0) return;
    return [...todoList.slice(0, index), ...todoList.slice(index + 1)];
};

const changeTodo = (todoList, newTodo) => {
    const index = todoList.findIndex(todo => todo.id === newTodo.id);
    todoList[index] = newTodo;
    return [...todoList];
}

export default TodoReducer;