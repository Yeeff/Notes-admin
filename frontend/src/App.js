import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import ArchiveNotesPage from './pages/ArchivedNotesPage'
import NotesForm from './components/NotesForm';
import LoginPage from './pages/LoginPage';
import PrivateRoute from './components/PrivateRoute';
import './App.css';

function App() {

  return (

    <Router>
      <div className='app-container'>
        <div className="App">
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/" element={<PrivateRoute>  <Home />  </PrivateRoute>} />
            <Route path="/archive-notes" element={<PrivateRoute>  <ArchiveNotesPage />  </PrivateRoute>  } />
            <Route path="/form-notes/:noteId" element={<PrivateRoute> <NotesForm /> </PrivateRoute>  } />
            <Route path="/form-notes" element={<PrivateRoute> <NotesForm /> </PrivateRoute>} />
          </Routes>
        </div>
      </div>
    </Router>

  );
}

export default App;
