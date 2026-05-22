import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../../api/axios';
import { 
    IconStethoscope, 
    IconMail, 
    IconLock, 
    IconUser,
    IconUsers,
    IconHeartHandshake,
    IconUserPlus,
    IconAlertCircle,
    IconLoader2
} from '@tabler/icons-react';
import '../../styles/Auth.css';

const Register = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        role: 'USER'
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
        if (error) setError('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            await api.post('/auth/register', formData);
            // After registration, redirect to login with a success message (could be handled via state)
            navigate('/login', { state: { message: 'Account created successfully! Please login.' } });
        } catch (err) {
            console.error('Registration error:', err);
            setError(err.response?.data?.message || 'Failed to create account. Email might already be in use.');
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
                        <h2>Join the community</h2>
                        <p>Create an account to save lives and share knowledge</p>
                    </div>

                    {error && (
                        <div className="error-alert">
                            <IconAlertCircle size={18} />
                            {error}
                        </div>
                    )}

                    <form className="auth-form" onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Full Name</label>
                            <div className="input-wrapper">
                                <IconUser size={18} />
                                <input
                                    id="name"
                                    type="text"
                                    name="name"
                                    className="auth-input"
                                    placeholder="John Doe"
                                    value={formData.name}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                        </div>

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
                                    value={formData.email}
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
                                    value={formData.password}
                                    onChange={handleChange}
                                    required
                                    minLength={6}
                                />
                            </div>
                        </div>

                        <div className="form-group">
                            <label>What is your role?</label>
                            <div className="role-selector">
                                <label className="role-option">
                                    <input 
                                        type="radio" 
                                        name="role" 
                                        value="USER" 
                                        checked={formData.role === 'USER'}
                                        onChange={handleChange}
                                    />
                                    <div className="role-card">
                                        <IconUsers size={24} />
                                        <span>User</span>
                                    </div>
                                </label>
                                <label className="role-option">
                                    <input 
                                        type="radio" 
                                        name="role" 
                                        value="VOLUNTEER" 
                                        checked={formData.role === 'VOLUNTEER'}
                                        onChange={handleChange}
                                    />
                                    <div className="role-card">
                                        <IconHeartHandshake size={24} />
                                        <span>Volunteer</span>
                                    </div>
                                </label>
                            </div>
                        </div>

                        <button type="submit" className="btn-auth" disabled={loading}>
                            {loading ? (
                                <IconLoader2 size={20} className="animate-spin" />
                            ) : (
                                <>
                                    <IconUserPlus size={20} />
                                    Create Account
                                </>
                            )}
                        </button>
                    </form>

                    <div className="auth-footer">
                        Already have an account? 
                        <Link to="/login">Login here</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Register;
