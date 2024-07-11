import '../styles/pages/ArchivedNotesPage.css'
import React from 'react';
import GenerilList from '../components/GenericList';
import { getArchivedNotes, unarchiveNote } from '../services/NoteApi';
import { useNavigate } from 'react-router-dom';

const ArchiveNotesPage = () => {

    const navegate = useNavigate();
    return (
        <div className='archived_page'>
            <i onClick={() => navegate('/')} class="fa fa-arrow-left" aria-hidden="true"
                style={{ fontSize: "30px" }}></i>
            <div className='archived_title'>
                <h1>Archived Notes</h1>
            </div>
            <GenerilList getNotesList={getArchivedNotes} handleArchiveRequest={unarchiveNote} ></GenerilList>
        </div>
    );
};

export default ArchiveNotesPage;
