import { IconStethoscope, IconLogout } from '@tabler/icons-react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="landing-nav">
            <div className="logo">
                <div className="logo-icon">
                    <IconStethoscope size={20} stroke={2} aria-hidden="true" />
                </div>
                <span className="logo-text">Med<span>Library</span></span>
            </div>
            <ul className="nav-links">
                <li><Link to="/">Home</Link></li>
                <li><Link to="/library">Library</Link></li>
                <li><Link to="/donations">Donations</Link></li>
                <li><Link to="/campaigns">Campaigns</Link></li>
                <li><Link to="/articles">Articles</Link></li>
                {user?.role === 'ADMIN' && <li><Link to="/admin">Admin Dashboard</Link></li>}
            </ul>
            <div className="nav-actions">
                {user ? (
                    <div className="user-profile-nav" style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
                        <div className="user-info" style={{ textAlign: 'right' }}>
                            <p style={{ fontSize: '14px', fontWeight: 600, color: 'var(--gray-800)', margin: 0 }}>{user.name}</p>
                            <span style={{ fontSize: '12px', color: 'var(--green-600)', textTransform: 'capitalize' }}>{user.role}</span>
                        </div>
                        <button className="btn-logout" onClick={handleLogout} style={{ background: 'none', border: 'none', color: 'var(--gray-400)', cursor: 'pointer', padding: '5px' }}>
                            <IconLogout size={20} />
                        </button>
                    </div>
                ) : (
                    <>
                        <Link to="/login">
                            <button className="btn-outline">Login</button>
                        </Link>
                        <Link to="/register">
                            <button className="btn-primary">Register</button>
                        </Link>
                    </>
                )}
            </div>
        </nav>
    );
};

export default Navbar;
