import React, { useState, useEffect } from 'react';
import { deleteNote , activeNotesByTagId } from '../services/NoteApi';
import { getAllTags } from '../services/TagApi';
import Note from './Note';
import '../styles/components/GenericList.css'

const GenerilList = ({ getNotesList, handleArchiveRequest }) => {
  const [notes, setNotes] = useState([]);
  const [tags, setTags] = useState([]);


  const [selectedTag, setSelectedtag] = useState();

  useEffect(() => {
    fetchNotes();
    fetchTags();

    fetchTags();
  }, []);

  const handleSelectChange = async (event) => {
    setSelectedtag(event.target.value);
    if(event.target.value == ""){
      const responseWithOutFilter = await getNotesList()
      setNotes(responseWithOutFilter.data);
    }else{
      const response = await activeNotesByTagId(event.target.value)
      setNotes(response.data);
    }
  };

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

      <div className='filter_select'>
        <select class="form-select form-select-sm" aria-label="Small select example" id="note-select" value={selectedTag} onChange={handleSelectChange}>
          <option value="">All tags</option>
          {tags.map((tag) => (
            <option key={tag.id} value={tag.id}>
              {tag.name}
            </option>
          ))}
        </select>
      </div>

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