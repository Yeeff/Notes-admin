import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import ArchiveNotesPage from './pages/ArchivedNotesPage'
import NotesForm from './components/NotesForm';
import LoginPage from './pages/LoginPage';
import Unauthorized from './pages/Unauthorized'
import PrivateRoute from './components/PrivateRoute';
import './App.css';

function App() {

  return (

    <Router>
      <div className='app-container'>
        <div className="App">
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/unauthorized" element={<Unauthorized />} />

            <Route
              path="/"
              element={
                <PrivateRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
                  <Home />
                </PrivateRoute>
              }
            />

            <Route
              path="/archive-notes"
              element={
                <PrivateRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
                  <ArchiveNotesPage />
                </PrivateRoute>
              }
            />
            <Route
              path="/form-notes/:noteId"
              element={
                <PrivateRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
                  <NotesForm />
                </PrivateRoute>
              }
            />
            <Route
              path="/form-notes/"
              element={
                <PrivateRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
                  <NotesForm />
                </PrivateRoute>
              }
            />
          </Routes>
        </div>
      </div>
    </Router>

  );
}

export default App;
