import {useContext} from "react";
import {TodoStatusListContext} from "../context/TodoContext.js";

const colorList = [ 'blue', 'purple', 'green' ]
export default function TodoStatusBadge({ status }) {
    const statusList = useContext(TodoStatusListContext);

    const getStatusName = (status) => {
        return statusList.filter(s => s.id === status)[0]?.description;
    }

    return (
        <span
            className={`bg-${colorList[status]}-100 text-${colorList[status]}-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-${colorList[status]}-400 border border-${colorList[status]}-500`}>
            {getStatusName(status)}
        </span>
    )
}