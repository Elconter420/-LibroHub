import React, { useState } from 'react'

export default function App(){
  const [email,setEmail]=useState('estudiante@universidad.edu')
  const [password,setPassword]=useState('estudiante123')
  const [token,setToken]=useState(null)
  const [books,setBooks]=useState([])

  async function login(){
    const res = await fetch('/api/auth/login', {
      method:'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify({email,password})
    });
    const data = await res.json();
    if (data.token) setToken(data.token);
  }

  async function fetchBooks(){
    const res = await fetch('/api/catalog/books', {
      headers: token ? { Authorization: 'Bearer '+token } : {}
    });
    const data = await res.json();
    setBooks(data);
  }

  return (
    <div style={{padding:20}}>
      <h1>LibroHub MVP</h1>
      <div style={{marginBottom:20}}>
        <input value={email} onChange={e=>setEmail(e.target.value)} placeholder="email"/>
        <input value={password} onChange={e=>setPassword(e.target.value)} placeholder="password" type="password"/>
        <button onClick={login}>Login</button>
        {token && <span style={{marginLeft:10}}>Token OK</span>}
      </div>
      <div>
        <button onClick={fetchBooks}>Listar libros</button>
        <pre>{JSON.stringify(books, null, 2)}</pre>
      </div>
    </div>
  )
}
