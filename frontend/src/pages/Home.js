import React, { useState, useEffect } from 'react';
import '../styles/pages/Home.css'
import GenerilList from '../components/GenericList';
import { getActiveNotes, archiveNote } from '../services/NoteApi';
import { useNavigate } from 'react-router-dom';
import useLogout from '../hooks/useLogout';


const Home = () => {

  const navegate = useNavigate();
  const handleLogout = useLogout();

  return (
    <div className='home'>
      <div className='controls'>

        <p><a onClick={() => navegate('/archive-notes')} class="link-offset-2" href="#">Archived Notes</a></p>

        <div className='add_control'>
          <i onClick={() => navegate('/form-notes')} class="fa fa-plus-circle" ></i>
          <span>Add note</span>

          <i class="fa fa-sign-out" aria-hidden="true" onClick={handleLogout} ></i>

        </div>

      </div>

      <div className='actives_title'>
        <h1>Active Notes</h1>
      </div>
      <GenerilList getNotesList={getActiveNotes} handleArchiveRequest={archiveNote} tagId={1}></GenerilList>
    </div>
  );
};

export default Home;
