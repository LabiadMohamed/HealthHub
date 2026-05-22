import { Routes, Route } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import Donations from './pages/donation/Donations';
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
import Library from './pages/library/Library';
import CampaignsPage from './pages/CampaignsPage';
import AdminDashboard from './pages/admin/AdminDashboard';
import ProtectedRoute from './routes/ProtectedRoute';
import './App.css';

function App() {
  return (
    <div className="App">
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/donations" element={<Donations />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/library" element={<Library />} />
        <Route path="/campaigns" element={<CampaignsPage />} />

        {/* Protected Routes */}
        <Route path="/admin" element={
          <ProtectedRoute requiredRole="ADMIN">
            <AdminDashboard />
          </ProtectedRoute>
        } />
      </Routes>
    </div>
  );
}

export default App;
