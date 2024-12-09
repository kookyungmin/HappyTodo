import TodoStatusBadge from "./TodoBadge.jsx";

export default function TodoCard({ todo, className }) {
    return (
        <div
            className={`${className} w-full max-w-md p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-8 dark:bg-gray-800 dark:border-gray-700`}>
            <div className="flex items-center justify-between mb-4">
                <h5 className="text-xl font-bold leading-none text-gray-900 dark:text-white">{todo.title}</h5>
                <TodoStatusBadge status={todo.statusCode} />
            </div>
            <div className="flow-root">
                {todo.content}
            </div>
        </div>
    )
}