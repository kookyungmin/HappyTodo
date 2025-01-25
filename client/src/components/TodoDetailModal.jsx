import {Modal} from "flowbite-react";
import { FaPlus } from "react-icons/fa";
import { FaRegTrashAlt } from "react-icons/fa";
import {removeTodoAction, saveTodoAction} from "../service/TodoService.js";
import {useEffect, useState} from "react";


export default function TodoDetailModal({ openModal, onClose, todo, onChange, onRemove }) {
    const [ title, setTitle ] = useState(todo.title);
    const [ isEditTitle, setIsEditTitle ] = useState(false);

    const removeTodo = async () => {
        if (!confirm('Are you sure to Remove?')) return;
        const { isError, data } = await removeTodoAction(todo.id);
        if (isError) {
            alert(`${data.errorMessage}`);
            return;
        }
        onRemove(todo);
        closeModal();
    };

    const onEnter = (event) => {
        if (event.code !== 'Enter') return;
        saveTodo({
            id: todo.id,
            title: title,
            content: todo.content,
            statusCode: todo.statusCode
        })
    }

    const saveTodo = async (newTodo) => {
        const { isError, data } = await saveTodoAction(newTodo);
        if (isError) {
            alert(data.errorMessage);
            return;
        }
        setIsEditTitle(false);
        onChange(data);
    };

    const closeModal = () => {
        initialize();
        onClose();
    };

    useEffect(() => {
        initialize();
    }, [ todo ]);

    const initialize = () => {
        setIsEditTitle(false);
        setTitle(todo.title);
    };

    return(
        <>
            <Modal show={openModal} onClose={closeModal}>
                <Modal.Header>
                    {isEditTitle ?
                        <>
                            <input value={title}
                                   onChange={(event) => setTitle(event.target.value) }
                                   onKeyUp={onEnter}/>
                        </>
                        : <div onClick={() => setIsEditTitle(true)}>{title}</div>}
                    <div className={'text-gray-500 cursor-pointer'}
                         style={{ position: 'absolute', top: '25px', right: '65px' }}
                         onClick={removeTodo}>
                        <FaRegTrashAlt />
                    </div>
                </Modal.Header>
                <Modal.Body>
                    <div className={'h-[100px]'}>
                        {todo.content}
                    </div>
                    <h2 className={'text-xl'}>Item</h2>
                    <hr/>
                    <ul className={'mt-4'}>
                        <li className={'text-base text-gray-500 flex items-center cursor-pointer'}>
                            <FaPlus className={'mr-2'} />
                            Add Item
                        </li>
                    </ul>
                </Modal.Body>
            </Modal>
        </>
    )
}