import TodoStatusTab from "../components/TodoStatusTab.jsx";
import { getTodoStatusListAction } from "../service/TodoService.js";
import { useEffect, useState } from "react";
import { TodoStatusListContext } from '../context/TodoContext';
import TodoList from "../components/TodoList.jsx";
import {useNavigate} from "react-router-dom";

export default function TodoListPage() {
    const [ todoStatusList, setTodoStatusList ] = useState([]);
    const [ activeTab, setActiveTab ] = useState(0);
    const navigate = useNavigate();

    const getTodoStatusList = async () => {
        const { isError, data } = await getTodoStatusListAction();
        if (isError) {
            alert(data.errorMessage);
            if (data.httpStatusCode === 401) {
                navigate('/login');
            }
            return;
        }
        setTodoStatusList(data);
    };

    useEffect(() => {
        getTodoStatusList();
    }, []);

    return (
        <TodoStatusListContext.Provider value={todoStatusList}>
            <h2 className={'text-2xl flex justify-center mt-4 mb-4 font-bold'}>TODO LIST</h2>
            {todoStatusList.length > 0 &&
                <>
                    <TodoStatusTab
                        dataSource={todoStatusList}
                        activeTab={activeTab}
                        onActiveTabChange={setActiveTab} />
                    <TodoList status={activeTab} />
                </>

            }
        </TodoStatusListContext.Provider>
    )
}