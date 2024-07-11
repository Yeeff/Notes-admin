import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/components/Note.css'
import TagIcon from './TagIcon '
import { assignTagToNote, removeTagToNote } from '../services/NoteApi';

const Note = ({ note, onDelete, onArchive, tags }) => {

  const navegate = useNavigate();

  const [tagsLocal, setTagsLocal] = useState(note.tags);

  const edit = (id) => {
    navegate(`/form-notes/${id}`)
  }

  const tagHandler = async (tag) => {
    const noteUpdate = await assignTagToNote(note.id, tag.id);
    setTagsLocal(noteUpdate.data.tags)
  }
  
  const deleteTagHandler = async (tagId)=>{

    const noteUpdate = await removeTagToNote(note.id, tagId);
    setTagsLocal(noteUpdate.data.tags)

  }

  return (
    <>
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">{note.title}</h5>
          <p class="card-text">{note.content}</p>

          {tagsLocal.map((tag)=>{
            return <span class="badge text-bg-warning">{tag.name} <a style={{cursor:"pointer"}} onClick={()=>deleteTagHandler(tag.id)}><b>x</b></a></span>
          })}
          

          <div>
            <i onClick={() => onDelete(note.id)} class="fa fa-trash" aria-hidden="true"
              style={{ color: "red", fontSize: "20px", marginRight: "10px" }}></i>
            <i onClick={() => onArchive(note.id)} class="fa fa-archive" aria-hidden="true"
              style={{ color: "blue", fontSize: "20px", marginRight: "10px" }}></i>
            <i onClick={() => edit(note.id)} class="fa fa-pencil" aria-hidden="true"
              style={{ color: "gray", fontSize: "20px", marginRight: "10px" }}></i>
              <TagIcon tags={tags} tagHandler={tagHandler}></TagIcon>
          </div>
          
        </div>
      </div>

    </>




  )
}

export default Note;