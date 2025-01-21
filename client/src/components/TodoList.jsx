import { useEffect, useState } from "react";
import {addTodoAction, getTodoListAction} from "../service/TodoService.js";
import TodoCard from "./TodoCard.jsx";
import LoadPanel from "./LoadPanel.jsx";
import TodoAddModal from "./TodoAddModal.jsx";

export default function TodoList({ status }) {
    const [ isLoading, setIsLoading ] = useState(false);
    const [ todoList, setTodoList ] = useState([]);
    const [ isOpenAddModal, setIsOpenAddModal ] = useState(false);

    const getTodoList = async (status) => {
        setIsLoading(true);
        const { isError, data } = await getTodoListAction(status);
        setIsLoading(false);

        if (isError) {
            alert(`ERROR !! >> ${data.errorMessage}`);
            return;
        }

        setTodoList(data);
    };

    const addTodo = async (todo) => {
        const { isError, data } = await addTodoAction(todo);
        if (isError) {
            alert(data.errorMessage);
            return;
        }
        console.log("####### ", data);
    }

    useEffect(() => {
        getTodoList(status);
    }, [ status ]);

    return (
        <>
            <LoadPanel isActive={isLoading} />
            <div className={'flex justify-end p-3'}>
                <button type="button"
                        className="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
                        onClick={() => setIsOpenAddModal(true)}>
                    Add
                </button>
            </div>
            {!isLoading && todoList.map(todo => {
                return (
                    <div key={todo.id} className={'flex justify-center mt-4'}>
                        <TodoCard todo={todo} className={'cursor-pointer'}/>
                    </div>
                )
            })}
            <TodoAddModal openModal={isOpenAddModal}
                          onAdd={addTodo}
                          onClose={() => setIsOpenAddModal(false)} />
        </>
    )
}