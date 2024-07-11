import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import ArchiveNotesPage from './pages/ArchivedNotesPage'
import NotesForm from './components/NotesForm';
import './App.css';

function App() {

  return (

    <Router>
      <div className='app-container'>
        <div className="App">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/archive-notes" element={<ArchiveNotesPage />} />
            <Route path="/form-notes/:noteId" element={<NotesForm />} />
            <Route path="/form-notes" element={<NotesForm />} />
          </Routes>
        </div>
      </div>
    </Router>

  );
}

export default App;
