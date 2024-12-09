import TodoStatusTab from "../components/TodoStatusTab.jsx";
import { fetchGetTodoStatusList } from "../service/TodoService.js";
import {useEffect, useState} from "react";

export default function TodoListPage() {
    const [ todoStatusList, setTodoStatusList ] = useState([]);
    const [ activeTab, setActiveTab ] = useState(0);

    const getTodoStatusList = async () => {
        const { isError, data } = await fetchGetTodoStatusList();
        if (isError) {
            alert(`ERROR !! >> ${data.errorMessage}`);
            return;
        }
        setTodoStatusList(data);
    };

    useEffect(() => {
        getTodoStatusList();
    }, []);

    useEffect(() => {
        console.log("###### ", activeTab);
    }, [ activeTab ]);

    return (
        <>
            <h2 className={'text-2xl flex justify-center mt-4 mb-4 font-bold'}>TODO LIST</h2>
            {todoStatusList.length > 0 &&
                <TodoStatusTab
                    dataSource={todoStatusList}
                    activeTab={activeTab}
                    onActiveTabChange={setActiveTab} />
            }

        </>
    )
}