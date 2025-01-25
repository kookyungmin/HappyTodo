import { createContext } from "react";

export const TodoStatusListContext = createContext([]);
export const TodoListContext = createContext({
    todoList: [],
    dispatch: null
});