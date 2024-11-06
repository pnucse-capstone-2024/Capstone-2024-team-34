import './App.css';
import Baseloyout from './layouts/BaseLayout';

import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Chat from './pages/Chat';
import Login from './pages/Login';
import Register from './pages/Register';
import FindPassword from './pages/FindPassword';
import EmailVerification from './pages/EmailVerification';

function App() {
  return (
    <Baseloyout>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="chat/:id" element={<Chat />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path="register/email-verification/:id" element={<EmailVerification />} />
        <Route path="login/find-password" element={<FindPassword />} />
      </Routes>
    </Baseloyout>
  );
}

export default App;
