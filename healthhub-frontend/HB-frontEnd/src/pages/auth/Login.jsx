import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import api from '../../api/axios';
import { 
    IconStethoscope, 
    IconMail, 
    IconLock, 
    IconLogin, 
    IconAlertCircle,
    IconLoader2
} from '@tabler/icons-react';
import '../../styles/Auth.css';

const Login = () => {
    const [credentials, setCredentials] = useState({ email: '', password: '' });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCredentials(prev => ({ ...prev, [name]: value }));
        if (error) setError('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const response = await api.post('/auth/login', credentials);
            login(response.data);
            navigate('/');
        } catch (err) {
            console.error('Login error:', err);
            setError(err.response?.data?.message || 'Invalid email or password. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-page">
            <div className="auth-container">
                <div className="auth-card">
                    <div className="auth-header">
                        <Link to="/" className="logo">
                            <div className="logo-icon">
                                <IconStethoscope size={24} stroke={2} aria-hidden="true" />
                            </div>
                            <span className="logo-text" style={{ color: '#fff' }}>Med<span>Library</span></span>
                        </Link>
                        <h2>Welcome back</h2>
                        <p>Login to your account to continue</p>
                    </div>

                    {error && (
                        <div className="error-alert">
                            <IconAlertCircle size={18} />
                            {error}
                        </div>
                    )}

                    <form className="auth-form" onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="email">Email Address</label>
                            <div className="input-wrapper">
                                <IconMail size={18} />
                                <input
                                    id="email"
                                    type="email"
                                    name="email"
                                    className="auth-input"
                                    placeholder="name@example.com"
                                    value={credentials.email}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <div className="input-wrapper">
                                <IconLock size={18} />
                                <input
                                    id="password"
                                    type="password"
                                    name="password"
                                    className="auth-input"
                                    placeholder="••••••••"
                                    value={credentials.password}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                        </div>

                        <button type="submit" className="btn-auth" disabled={loading}>
                            {loading ? (
                                <IconLoader2 size={20} className="animate-spin" />
                            ) : (
                                <>
                                    <IconLogin size={20} />
                                    Login Now
                                </>
                            )}
                        </button>
                    </form>

                    <div className="auth-footer">
                        Don't have an account? 
                        <Link to="/register">Create one</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Login;
