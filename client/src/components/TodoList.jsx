import { useEffect, useState } from "react";
import { getTodoListAction } from "../service/TodoService.js";
import TodoCard from "./TodoCard.jsx";
import LoadPanel from "./LoadPanel.jsx";

export default function TodoList({ status }) {
    const [ isLoading, setIsLoading ] = useState(false);
    const [ todoList, setTodoList ] = useState([]);
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

    useEffect(() => {
        getTodoList(status);
    }, [ status ]);

    return (
        <>
            <LoadPanel isActive={isLoading} />
            {!isLoading && todoList.map(todo => {
                return (
                    <div key={todo.id} className={'flex justify-center mt-4'}>
                        <TodoCard todo={todo} className={'cursor-pointer'}/>
                    </div>
                )
            })}
        </>
    )
}