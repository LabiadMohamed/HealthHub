import { useState, useEffect } from 'react';
import Navbar from '../../components/Navbar';
import Footer from '../../components/Footer';
import api from '../../api/axios';
import { 
    BLOOD_TYPE_MAP, 
    REVERSE_BLOOD_TYPE_MAP, 
    DONATION_TYPE_MAP, 
    REVERSE_DONATION_TYPE_MAP,
    DONATION_STATUS_MAP,
    formatRelativeTime
} from '../../utils/mappings';
import { 
    IconDroplet, 
    IconPill, 
    IconUsers, 
    IconChevronRight, 
    IconList, 
    IconUser, 
    IconPlus, 
    IconAlertTriangle, 
    IconMapPin, 
    IconFileText, 
    IconClock, 
    IconHeart, 
    IconCheck, 
    IconX,
    IconSend
} from '@tabler/icons-react';
import { Link } from 'react-router-dom';
import '../../styles/Donations.css';

const Donations = () => {
    const [currentTab, setCurrentTab] = useState('all');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [requests, setRequests] = useState([]);
    const [myRequests, setMyRequests] = useState([]);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');
    
    // Form state
    const [formData, setFormData] = useState({
        type: '',
        title: '',
        city: 'Casablanca', // Default or user city
        blood: '',
        details: '',
        urgent: false
    });

    const fetchDonations = async (showLoading = true, isMounted = true) => {
        if (showLoading && isMounted) setLoading(true);
        try {
            const response = await api.get('/donations/open');
            const mappedData = response.data.map(transformBackendDonation);
            if (isMounted) setRequests(mappedData);
        } catch (error) {
            console.error('Error fetching donations:', error);
            if (isMounted) setErrorMessage('Unable to load requests. Please try again.');
        } finally {
            if (isMounted) setLoading(false);
        }
    };

    const fetchMyDonations = async (isMounted = true) => {
        try {
            const response = await api.get('/donations/my');
            const mappedData = response.data.map(transformBackendDonation);
            if (isMounted) setMyRequests(mappedData);
        } catch (error) {
            console.error('Error fetching my donations:', error);
        }
    };

    const transformBackendDonation = (d) => ({
        id: d.id,
        type: DONATION_TYPE_MAP[d.type] || 'blood',
        title: d.type === 'BLOOD' ? `Urgent ${BLOOD_TYPE_MAP[d.bloodType] || ''} blood` : d.medicationName,
        city: 'Casablanca', // Backend should ideally provide city, using default for now
        blood: BLOOD_TYPE_MAP[d.bloodType] || '',
        details: d.notes || '',
        time: formatRelativeTime(d.createdAt),
        status: DONATION_STATUS_MAP[d.status] || 'open'
    });

    useEffect(() => {
        let isMounted = true;
        // Wrapping in Promise.resolve() ensures this call is truly async to the effect body
        // and satisfies the "no sync setState" rule
        Promise.resolve().then(() => fetchDonations(false, isMounted));
        return () => { isMounted = false; };
    }, []);

    useEffect(() => {
        let isMounted = true;
        if (currentTab === 'mine') {
            Promise.resolve().then(() => fetchMyDonations(isMounted));
        }
        return () => { isMounted = false; };
    }, [currentTab]);

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');
        
        const payload = {
            type: REVERSE_DONATION_TYPE_MAP[formData.type],
            bloodType: formData.type === 'blood' ? REVERSE_BLOOD_TYPE_MAP[formData.blood] : null,
            medicationName: formData.type === 'med' ? formData.title : null,
            notes: formData.details
        };

        try {
            await api.post('/donations', payload);
            setIsModalOpen(false);
            setFormData({
                type: '',
                title: '',
                city: 'Casablanca',
                blood: '',
                details: '',
                urgent: false
            });
            setCurrentTab('mine');
            fetchMyDonations();
            fetchDonations(); // Refresh open donations too
        } catch (error) {
            console.error('Error creating donation:', error);
            setErrorMessage('Error while publishing. Please check your connection.');
        }
    };

    const handleRespond = async (id) => {
        try {
            await api.post(`/donations/${id}/respond`);
            // Refresh counts and lists
            fetchDonations();
            if (currentTab === 'mine') fetchMyDonations();
        } catch (error) {
            console.error('Error responding to donation:', error);
            setErrorMessage('Unable to respond to this request at the moment.');
        }
    };

    const filteredRequests = () => {
        let list = currentTab === 'mine' ? myRequests : requests;
        if (currentTab === 'blood') list = list.filter(r => r.type === 'blood');
        if (currentTab === 'med') list = list.filter(r => r.type === 'med');
        return list;
    };

    const getStatusLabel = (s) => {
        if (s === 'urgent') return (
            <span className="req-status urgent">
                <IconAlertTriangle size={14} style={{ verticalAlign: '-2px', marginRight: '4px' }} />
                Urgent
            </span>
        );
        if (s === 'open') return <span className="req-status open">Open</span>;
        return <span className="req-status done">Fulfilled</span>;
    };

    const counts = {
        all: requests.length,
        blood: requests.filter(r => r.type === 'blood').length,
        med: requests.filter(r => r.type === 'med').length
    };

    return (
        <div className="donations-page">
            <Navbar />
            
            <header className="page-header">
                <div className="page-header-inner">
                    <div className="breadcrumb">
                        <Link to="/">Home</Link>
                        <IconChevronRight size={14} />
                        <span>Donations</span>
                    </div>
                    <h1 className="page-title">Medical Solidarity</h1>
                    <p className="page-desc">Post or respond to blood and medicine donation requests. Every gesture counts to save lives.</p>
                </div>
            </header>

            <section className="stats-bar">
                <div className="stats-inner">
                    <div className="stat-item">
                        <div className="stat-icon blood"><IconDroplet size={22} /></div>
                        <div className="stat-info">
                            <h4>{counts.blood}</h4>
                            <p>Active blood requests</p>
                        </div>
                    </div>
                    <div className="stat-item">
                        <div className="stat-icon med"><IconPill size={22} /></div>
                        <div className="stat-info">
                            <h4>{counts.med}</h4>
                            <p>Medicine requests</p>
                        </div>
                    </div>
                    <div className="stat-item">
                        <div className="stat-icon vol"><IconUsers size={22} /></div>
                        <div className="stat-info">
                            <h4>156</h4>
                            <p>Donors this month</p>
                        </div>
                    </div>
                </div>
            </section>

            <section className="tabs-section">
                <div className="tabs-inner">
                    <button 
                        className={`tab-btn ${currentTab === 'all' ? 'active' : ''}`} 
                        onClick={() => setCurrentTab('all')}
                    >
                        <IconList size={18} /> All <span className="badge">{counts.all}</span>
                    </button>
                    <button 
                        className={`tab-btn ${currentTab === 'blood' ? 'active' : ''}`} 
                        onClick={() => setCurrentTab('blood')}
                    >
                        <IconDroplet size={18} /> Blood <span className="badge">{counts.blood}</span>
                    </button>
                    <button 
                        className={`tab-btn ${currentTab === 'med' ? 'active' : ''}`} 
                        onClick={() => setCurrentTab('med')}
                    >
                        <IconPill size={18} /> Medicines <span className="badge">{counts.med}</span>
                    </button>
                    <button 
                        className={`tab-btn ${currentTab === 'mine' ? 'active' : ''}`} 
                        onClick={() => setCurrentTab('mine')}
                    >
                        <IconUser size={18} /> My requests
                    </button>
                </div>
            </section>

            <section className="requests-section">
                <div className="requests-inner">
                    <div className="requests-header">
                        <h2>
                            {currentTab === 'mine' ? 'My requests' : 
                            currentTab === 'blood' ? 'Blood requests' : 
                            currentTab === 'med' ? 'Medicine requests' : 
                            'Recent requests'}
                        </h2>
                        <button className="btn-new" onClick={() => setIsModalOpen(true)}>
                            <IconPlus size={18} />New request
                        </button>
                    </div>

                    {errorMessage && (
                        <div style={{ color: '#D32F2F', backgroundColor: '#FFEBEE', padding: '12px', borderRadius: '8px', marginBottom: '20px', fontSize: '14px', textAlign: 'center' }}>
                            <IconAlertTriangle size={16} style={{ verticalAlign: '-3px', marginRight: '8px' }} />
                            {errorMessage}
                        </div>
                    )}
                    
                    <div className="requests-grid">
                        {loading ? (
                            <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '40px' }}>Loading...</div>
                        ) : filteredRequests().length > 0 ? (
                            filteredRequests().map(r => (
                                <div className="request-card" key={r.id}>
                                    <div className="request-top">
                                        <div className={`req-badge ${r.type}`}>
                                            {r.type === 'blood' ? <IconDroplet size={24} /> : <IconPill size={24} />}
                                        </div>
                                        <div className="req-info">
                                            <h4>{r.title}</h4>
                                            <p>{r.city} · {r.time}</p>
                                        </div>
                                        {getStatusLabel(r.status)}
                                    </div>
                                    <div className="request-body">
                                        {r.type === 'blood' && (
                                            <div className="req-detail">
                                                <IconDroplet size={15} />
                                                <strong>Group:</strong> {r.blood}
                                            </div>
                                        )}
                                        <div className="req-detail"><IconMapPin size={15} />{r.city}</div>
                                        <div className="req-detail"><IconFileText size={15} />{r.details}</div>
                                    </div>
                                    <div className="request-footer">
                                        <span className="req-time"><IconClock size={14} style={{ verticalAlign: '-2px', marginRight: '4px' }} />{r.time}</span>
                                        <button 
                                            className="btn-respond" 
                                            disabled={r.status === 'done'}
                                            onClick={() => handleRespond(r.id)}
                                        >
                                            {r.status === 'done' ? (
                                                <><IconCheck size={14} style={{ verticalAlign: '-2px', marginRight: '4px' }} />Fulfilled</>
                                            ) : (
                                                <><IconHeart size={14} style={{ verticalAlign: '-2px', marginRight: '4px' }} />Respond</>
                                            )}
                                        </button>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '60px 20px', color: 'var(--gray-400)' }}>
                                <IconFileText size={48} style={{ display: 'block', margin: '0 auto 16px', opacity: '.4' }} />
                                <p>{currentTab === 'mine' ? "You haven't published a request yet." : "No requests found."}</p>
                                {currentTab === 'mine' && (
                                    <button className="btn-new" style={{ margin: '16px auto 0' }} onClick={() => setIsModalOpen(true)}>
                                        Create a request
                                    </button>
                                )}
                            </div>
                        )}
                    </div>
                </div>
            </section>

            {/* MODAL */}
            <div className={`modal-overlay ${isModalOpen ? 'active' : ''}`} onClick={(e) => e.target.classList.contains('modal-overlay') && setIsModalOpen(false)}>
                <div className="modal">
                    <div className="modal-header">
                        <h3>New request</h3>
                        <button className="modal-close" onClick={() => setIsModalOpen(false)}><IconX size={20} /></button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleFormSubmit}>
                            <div className="form-group">
                                <label>Request type</label>
                                <select 
                                    value={formData.type} 
                                    onChange={(e) => setFormData({...formData, type: e.target.value})} 
                                    required
                                >
                                    <option value="">Choose...</option>
                                    <option value="blood">Blood Donation</option>
                                    <option value="med">Medicine Donation</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <label>{formData.type === 'med' ? 'Medicine name' : 'Title / Short description'}</label>
                                <input 
                                    type="text" 
                                    placeholder={formData.type === 'med' ? "Ex: Insulin Glargine" : "Ex: O- blood urgent for surgery"} 
                                    value={formData.title} 
                                    onChange={(e) => setFormData({...formData, title: e.target.value})} 
                                    required 
                                />
                            </div>
                            <div className="form-row">
                                <div className="form-group">
                                    <label>City</label>
                                    <select 
                                        value={formData.city} 
                                        onChange={(e) => setFormData({...formData, city: e.target.value})} 
                                        required
                                    >
                                        <option value="">Choose...</option>
                                        <option value="Casablanca">Casablanca</option>
                                        <option value="Rabat">Rabat</option>
                                        <option value="Marrakech">Marrakech</option>
                                        <option value="Fès">Fès</option>
                                        <option value="Tanger">Tanger</option>
                                        <option value="Agadir">Agadir</option>
                                    </select>
                                </div>
                                <div className="form-group">
                                    <label>Blood group (if blood)</label>
                                    <select 
                                        value={formData.blood} 
                                        onChange={(e) => setFormData({...formData, blood: e.target.value})}
                                        disabled={formData.type === 'med'}
                                    >
                                        <option value="">N/A</option>
                                        <option value="A+">A+</option>
                                        <option value="A-">A-</option>
                                        <option value="B+">B+</option>
                                        <option value="B-">B-</option>
                                        <option value="AB+">AB+</option>
                                        <option value="AB-">AB-</option>
                                        <option value="O+">O+</option>
                                        <option value="O-">O-</option>
                                    </select>
                                </div>
                            </div>
                            <div className="form-group">
                                <label>Additional details</label>
                                <textarea 
                                    placeholder="Describe the situation, quantity needed, contact..." 
                                    value={formData.details} 
                                    onChange={(e) => setFormData({...formData, details: e.target.value})}
                                ></textarea>
                                <p className="form-hint">Be specific to facilitate responses.</p>
                            </div>
                            <div className="form-group">
                                <label style={{ display: 'flex', alignItems: 'center', gap: '8px', cursor: 'pointer' }}>
                                    <input 
                                        type="checkbox" 
                                        checked={formData.urgent} 
                                        onChange={(e) => setFormData({...formData, urgent: e.target.checked})}
                                        style={{ width: 'auto', margin: 0, accentColor: 'var(--green-600)' }} 
                                    />
                                    Marquer comme urgent
                                </label>
                            </div>
                            <button type="submit" className="btn-submit">
                                <IconSend size={18} style={{ marginRight: '6px' }} />
                                Publish request
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <Footer />
        </div>
    );
};

export default Donations;
