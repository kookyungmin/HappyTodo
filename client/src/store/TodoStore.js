import { create } from "zustand";

const TodoStore = create((set) => ({
    todoList: [],
    todoStatusList: [],
    setTodoStatusList: (todoStatusList) => set((state) => ({ todoStatusList, todoList: state.todoList })),
    setTodoList: (todoList) => set((state) => ({ todoList, todoStatusList: state.todoStatusList })),
    addTodo: (todo) => set((state) => ({ todoList: [ ...state.todoList, todo ], todoStatusList: state.todoStatusList })),
    removeTodo: (id) => set((state) => ({ todoList: remove(state.todoList, id), todoStatusList: state.todoStatusList })),
    changeTodo: (todo) => set((state) => ({ todoList: update(state.todoList, todo), todoStatusList: state.todoStatusList }))
}));

const remove = (todoList, id) => {
    const index = todoList.findIndex(todo => todo.id === id);
    if (index < 0) return;
    return [...todoList.slice(0, index), ...todoList.slice(index + 1)];
};

const update = (todoList, newTodo) => {
    const index = todoList.findIndex(todo => todo.id === newTodo.id);
    todoList[index] = newTodo;
    return [...todoList];
}

export default TodoStore;