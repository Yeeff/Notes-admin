import '../styles/components/NotesForm.css'
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getNoteById, updateNote, createNote } from '../services/NoteApi';

const NotesForm = () => {

    const navegate = useNavigate();

    const { noteId } = useParams();

    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    useEffect(() => {

        if (noteId) {
            fetchNote(noteId);
        }
    }, [noteId]);

    const fetchNote = async (id) => {
        const response = await getNoteById(id);
        setTitle(response.data.title);
        setContent(response.data.content);
    };

    const handleSave = async () => {
        const note = {
            title,
            content
        };

        if (noteId) await updateNote(noteId, note)
        else await createNote(note)

        navegate('/')
    };

    return (
        <div className='notes_form'>
            <form>
                <legend>{noteId ? 'Edit Note' : 'Create Note'}</legend>
                <div class="mb-3">
                    <label for="titleInput" class="form-label">Title:</label>
                    <input type="text" id="titleInput" class="form-control"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    ></input>
                </div>
                <div class="mb-3">
                    <label for="contentInput" class="form-label">Content:</label>
                    <textarea type="text" id="contentInput" class="form-control"
                        value={content}
                        onChange={(e) => setContent(e.target.value)} ></textarea>
                </div>

                <button onClick={() => navegate('/')} class="btn btn-secondary">Cancel</button>
                <button type="button" onClick={handleSave} class="btn btn-primary">
                    {noteId ? 'Update Note' : 'Create Note'}
                </button>
            </form>

        </div>
    );
};

export default NotesForm;
