import React, { useState, useEffect } from 'react';
import { deleteNote } from '../services/NoteApi';
import { getAllTags } from '../services/TagApi';
import Note from './Note';
import '../styles/components/GenericList.css'

const GenerilList = ({ getNotesList, handleArchiveRequest }) => {
  const [notes, setNotes] = useState([]);
  const [tags, setTags] = useState([]);

  useEffect(() => {
    fetchNotes();
    fetchTags();
  }, []);

  const fetchNotes = async () => {
    const response = await getNotesList();
    setNotes(response.data);
  };
  
  const fetchTags = async () => {
    const response = await getAllTags();
    setTags(response.data);
  };

  const handleDelete = async (id) => {
    await deleteNote(id);
    fetchNotes();
  };

  const handleArchive = async (id) => {
    await handleArchiveRequest(id);
    fetchNotes();
  };

  return (
    <>
      {notes.length == 0 && <div class="alert alert-info" role="alert"> There's not notes to show. Create one.</div>}
      <div className='list'>
        {notes.map(note => (
          <Note className={'item'} key={note.id} note={note} onDelete={handleDelete} onArchive={handleArchive} tags={tags} />
        ))}
      </div>
    </>
  );
};

export default GenerilList;