import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/components/Note.css'
import TagIcon from './TagIcon '
import { assignTagToNote } from '../services/TagApi';

const Note = ({ note, onDelete, onArchive, tags }) => {

  const navegate = useNavigate();

  const edit = (id) => {
    navegate(`/form-notes/${id}`)
  }

  const tagHandler = async (tag)=>{
    console.log(tag);  
    console.log(note);
    const noteUpdate= await assignTagToNote(note.id, tag.id);
    console.log(noteUpdate);
  }

  return (
    <>
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">{note.title}</h5>
          <p class="card-text">{note.content}</p>

          <i onClick={() => onDelete(note.id)} class="fa fa-trash" aria-hidden="true"
            style={{ color: "red", fontSize: "20px", marginRight: "10px" }}></i>
          <i onClick={() => onArchive(note.id)} class="fa fa-archive" aria-hidden="true"
            style={{ color: "blue", fontSize: "20px", marginRight: "10px" }}></i>
          <i onClick={() => edit(note.id)} class="fa fa-pencil" aria-hidden="true"
            style={{ color: "gray", fontSize: "20px", marginRight: "10px" }}></i>

          <TagIcon tags={tags} tagHandler={tagHandler}></TagIcon>
        </div>
      </div>

    </>




  )
}

export default Note;