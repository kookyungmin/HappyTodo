import { useRef } from "react";
import { uploadFilesAction } from "../service/FileService.js";

export default function FileUploader({ children, multiple, accept = 'image/*', onUploaded, onError }) {
    const inputRef = useRef(null);
    const uploadFile = async (event) => {
        const files = event.target.files;
        const formData = new FormData();
        for(let file of files) {
            formData.append('files', file);
        }
        const { data, isError } = await uploadFilesAction(formData);
        if (isError) {
            console.error(data.errorMessage);
            if (onError) {
                onError(data);
            }
            return;
        }

        if (onUploaded) {
            onUploaded(data);
        }
    };

    return (
        <>
            {inputRef &&
                <>
                    <input type={'file'}
                           ref={inputRef}
                           multiple={multiple}
                           accept={accept}
                           hidden={true}
                           onChange={uploadFile} />
                    <div onClick={() => inputRef.current.click()}>
                        {children}
                    </div>
                </>
            }
        </>
    )
}