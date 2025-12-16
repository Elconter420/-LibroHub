import React, { useState } from 'react'
import './App.css'

export default function App() {
  const [email, setEmail] = useState('estudiante@universidad.edu')
  const [password, setPassword] = useState('estudiante123')
  const [token, setToken] = useState(null)
  const [books, setBooks] = useState([])
  const [loading, setLoading] = useState(false)

  async function login() {
    setLoading(true)
    try {
      const res = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      const data = await res.json();
      if (data.token) setToken(data.token);
    } catch (error) {
      console.error('Login error:', error);
    } finally {
      setLoading(false)
    }
  }

  async function fetchBooks() {
    setLoading(true)
    try {
      const res = await fetch('/api/catalog/books', {
        headers: token ? { Authorization: 'Bearer ' + token } : {}
      });
      const data = await res.json();
      setBooks(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error('Fetch books error:', error);
      setBooks([]);
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="app-container">
      <header className="app-header">
        <h1 className="app-title">LibroHub</h1>
        <p className="app-subtitle">Sistema de Gestión Bibliotecaria</p>
      </header>

      <div className="login-section">
        <div className="glass-card">
          <div className="section-header">
            <h2 className="section-title">Iniciar Sesión</h2>
          </div>

          <form className="login-form" onSubmit={(e) => { e.preventDefault(); login(); }}>
            <div className="form-group">
              <label className="form-label">Email</label>
              <input
                className="form-input"
                type="email"
                value={email}
                onChange={e => setEmail(e.target.value)}
                placeholder="usuario@universidad.edu"
              />
            </div>

            <div className="form-group">
              <label className="form-label">Contraseña</label>
              <input
                className="form-input"
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                placeholder="••••••••"
              />
            </div>

            <button
              type="submit"
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? 'Cargando...' : 'Iniciar Sesión'}
            </button>

            {token && (
              <div className="status-badge">
                Sesión activa
              </div>
            )}
          </form>
        </div>
      </div>

      <div className="books-section">
        <div className="glass-card">
          <div className="section-header">
            <h2 className="section-title">Catálogo de Libros</h2>
          </div>

          <button
            onClick={fetchBooks}
            className="btn btn-secondary"
            disabled={loading}
          >
            {loading ? 'Cargando...' : 'Cargar Libros'}
          </button>

          {books.length > 0 ? (
            <div className="books-grid">
              {books.map((book, index) => (
                <div key={book.id || index} className="book-card">
                  <h3 className="book-title">{book.title || book.nombre || 'Sin título'}</h3>
                  <p className="book-author">
                    {book.author || book.autor || 'Autor desconocido'}
                  </p>
                  {book.isbn && (
                    <div className="book-meta">
                      <span className="book-tag">ISBN: {book.isbn}</span>
                    </div>
                  )}
                  {book.available !== undefined && (
                    <div className="book-meta">
                      <span className="book-tag">
                        {book.available ? 'Disponible' : 'No disponible'}
                      </span>
                    </div>
                  )}
                </div>
              ))}
            </div>
          ) : !loading && (
            <div className="empty-state">
              <p className="empty-message">
                No hay libros para mostrar. Haz clic en "Cargar Libros" para ver el catálogo.
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
