import { Button, Modal } from "flowbite-react";
import { useState } from "react";

export default function TodoAddModal({ openModal, onClose, onAdd }) {
    const [ title, setTitle ] = useState('');
    const [ content, setContent ] = useState('');

    const clickAddBtn = () => {
        const data = {
            title,
            content
        }
        onAdd(data);
        initialize();
    };

    const closeModal = () => {
        initialize();
        onClose();
    };

    const initialize = () => {
        setTitle('');
        setContent('');
    }

    return (
        <>
            <Modal show={openModal} onClose={closeModal}>
                <Modal.Header>Todo Add</Modal.Header>
                <Modal.Body>
                    <div>
                        <label htmlFor="title"
                               className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                            Title
                        </label>
                        <input type="text" id="title"
                               className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                               value={title}
                               onChange={(event) => setTitle(event.target.value)}
                               placeholder="Title" required/>
                    </div>
                    <div className={'mt-3'}>
                        <label htmlFor="description"
                               className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                            Description
                        </label>
                        <input type="text" id="description"
                               className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                               value={content}
                               onChange={(event) => setContent(event.target.value)}
                               placeholder="Description" required/>
                    </div>

                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={clickAddBtn}>Add</Button>
                    <Button color="gray" onClick={closeModal}>
                        Cancel
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}