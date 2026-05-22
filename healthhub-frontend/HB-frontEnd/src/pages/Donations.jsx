import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import api from '../services/api';
import { 
    BLOOD_TYPE_MAP, 
    REVERSE_BLOOD_TYPE_MAP, 
    DONATION_TYPE_MAP, 
    REVERSE_DONATION_TYPE_MAP,
    DONATION_STATUS_MAP,
    formatRelativeTime
} from '../utils/mappings';
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
import '../styles/Donations.css';

const Donations = () => {
    const [currentTab, setCurrentTab] = useState('all');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [requests, setRequests] = useState([]);
    const [myRequests, setMyRequests] = useState([]);
    const [loading, setLoading] = useState(false);
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

    const fetchDonations = async () => {
        setLoading(true);
        try {
            const response = await api.get('/donations/open');
            const mappedData = response.data.map(transformBackendDonation);
            setRequests(mappedData);
        } catch (error) {
            console.error('Error fetching donations:', error);
            setErrorMessage('Impossible de charger les demandes. Veuillez réessayer.');
        } finally {
            setLoading(false);
        }
    };

    const fetchMyDonations = async () => {
        try {
            const response = await api.get('/donations/my');
            const mappedData = response.data.map(transformBackendDonation);
            setMyRequests(mappedData);
        } catch (error) {
            console.error('Error fetching my donations:', error);
        }
    };

    const transformBackendDonation = (d) => ({
        id: d.id,
        type: DONATION_TYPE_MAP[d.type] || 'blood',
        title: d.type === 'BLOOD' ? `Sang ${BLOOD_TYPE_MAP[d.bloodType] || ''} urgent` : d.medicationName,
        city: 'Casablanca', // Backend should ideally provide city, using default for now
        blood: BLOOD_TYPE_MAP[d.bloodType] || '',
        details: d.notes || '',
        time: formatRelativeTime(d.createdAt),
        status: DONATION_STATUS_MAP[d.status] || 'open'
    });

    useEffect(() => {
        fetchDonations();
    }, []);

    useEffect(() => {
        if (currentTab === 'mine') {
            fetchMyDonations();
        }
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
            setErrorMessage('Erreur lors de la publication. Vérifiez votre connexion.');
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
            setErrorMessage('Impossible de répondre à cette demande pour le moment.');
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
        if (s === 'open') return <span className="req-status open">Ouvert</span>;
        return <span className="req-status done">Satisfait</span>;
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
                        <Link to="/">Accueil</Link>
                        <IconChevronRight size={14} />
                        <span>Donations</span>
                    </div>
                    <h1 className="page-title">Solidarité médicale</h1>
                    <p className="page-desc">Publiez ou répondez à des demandes de don de sang et de médicaments. Chaque geste compte pour sauver des vies.</p>
                </div>
            </header>

            <section className="stats-bar">
                <div className="stats-inner">
                    <div className="stat-item">
                        <div className="stat-icon blood"><IconDroplet size={22} /></div>
                        <div className="stat-info">
                            <h4>{counts.blood}</h4>
                            <p>Demandes de sang actives</p>
                        </div>
                    </div>
                    <div className="stat-item">
                        <div className="stat-icon med"><IconPill size={22} /></div>
                        <div className="stat-info">
                            <h4>{counts.med}</h4>
                            <p>Demandes de médicaments</p>
                        </div>
                    </div>
                    <div className="stat-item">
                        <div className="stat-icon vol"><IconUsers size={22} /></div>
                        <div className="stat-info">
                            <h4>156</h4>
                            <p>Donateurs ce mois-ci</p>
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
                        <IconList size={18} /> Toutes <span className="badge">{counts.all}</span>
                    </button>
                    <button 
                        className={`tab-btn ${currentTab === 'blood' ? 'active' : ''}`} 
                        onClick={() => setCurrentTab('blood')}
                    >
                        <IconDroplet size={18} /> Sang <span className="badge">{counts.blood}</span>
                    </button>
                    <button 
                        className={`tab-btn ${currentTab === 'med' ? 'active' : ''}`} 
                        onClick={() => setCurrentTab('med')}
                    >
                        <IconPill size={18} /> Médicaments <span className="badge">{counts.med}</span>
                    </button>
                    <button 
                        className={`tab-btn ${currentTab === 'mine' ? 'active' : ''}`} 
                        onClick={() => setCurrentTab('mine')}
                    >
                        <IconUser size={18} /> Mes demandes
                    </button>
                </div>
            </section>

            <section className="requests-section">
                <div className="requests-inner">
                    <div className="requests-header">
                        <h2>
                            {currentTab === 'mine' ? 'Mes demandes' : 
                             currentTab === 'blood' ? 'Demandes de sang' : 
                             currentTab === 'med' ? 'Demandes de médicaments' : 
                             'Demandes récentes'}
                        </h2>
                        <button className="btn-new" onClick={() => setIsModalOpen(true)}>
                            <IconPlus size={18} />Nouvelle demande
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
                            <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '40px' }}>Chargement...</div>
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
                                                <strong>Groupe :</strong> {r.blood}
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
                                                <><IconCheck size={14} style={{ verticalAlign: '-2px', marginRight: '4px' }} />Satisfait</>
                                            ) : (
                                                <><IconHeart size={14} style={{ verticalAlign: '-2px', marginRight: '4px' }} />Répondre</>
                                            )}
                                        </button>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '60px 20px', color: 'var(--gray-400)' }}>
                                <IconFileText size={48} style={{ display: 'block', margin: '0 auto 16px', opacity: '.4' }} />
                                <p>{currentTab === 'mine' ? "Vous n'avez pas encore publié de demande." : "Aucune demande trouvée."}</p>
                                {currentTab === 'mine' && (
                                    <button className="btn-new" style={{ margin: '16px auto 0' }} onClick={() => setIsModalOpen(true)}>
                                        Créer une demande
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
                        <h3>Nouvelle demande</h3>
                        <button className="modal-close" onClick={() => setIsModalOpen(false)}><IconX size={20} /></button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleFormSubmit}>
                            <div className="form-group">
                                <label>Type de demande</label>
                                <select 
                                    value={formData.type} 
                                    onChange={(e) => setFormData({...formData, type: e.target.value})} 
                                    required
                                >
                                    <option value="">Choisir...</option>
                                    <option value="blood">Don de sang</option>
                                    <option value="med">Don de médicaments</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <label>{formData.type === 'med' ? 'Nom du médicament' : 'Titre / Description courte'}</label>
                                <input 
                                    type="text" 
                                    placeholder={formData.type === 'med' ? "Ex: Insuline Glargine" : "Ex: Sang O- urgent pour opération"} 
                                    value={formData.title} 
                                    onChange={(e) => setFormData({...formData, title: e.target.value})} 
                                    required 
                                />
                            </div>
                            <div className="form-row">
                                <div className="form-group">
                                    <label>Ville</label>
                                    <select 
                                        value={formData.city} 
                                        onChange={(e) => setFormData({...formData, city: e.target.value})} 
                                        required
                                    >
                                        <option value="">Choisir...</option>
                                        <option value="Casablanca">Casablanca</option>
                                        <option value="Rabat">Rabat</option>
                                        <option value="Marrakech">Marrakech</option>
                                        <option value="Fès">Fès</option>
                                        <option value="Tanger">Tanger</option>
                                        <option value="Agadir">Agadir</option>
                                    </select>
                                </div>
                                <div className="form-group">
                                    <label>Groupe sanguin (si sang)</label>
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
                                <label>Détails supplémentaires</label>
                                <textarea 
                                    placeholder="Décrivez la situation, quantité nécessaire, contact..." 
                                    value={formData.details} 
                                    onChange={(e) => setFormData({...formData, details: e.target.value})}
                                ></textarea>
                                <p className="form-hint">Soyez précis pour faciliter les réponses.</p>
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
                                Publier la demande
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
