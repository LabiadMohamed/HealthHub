import { useState, useEffect } from 'react';
import api from '../services/api';
import { 
    BLOOD_TYPE_MAP, 
    DONATION_TYPE_MAP, 
    formatRelativeTime
} from '../utils/mappings';
import { IconDroplet, IconPill } from '@tabler/icons-react';
import { Link } from 'react-router-dom';

const DonationSection = () => {
    const [recentRequests, setRecentRequests] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        let isMounted = true;

        const fetchRecentRequests = async () => {
            try {
                const response = await api.get('/donations/open');
                // Take only the first 4 for the landing page board
                const mappedData = response.data.slice(0, 4).map(d => ({
                    id: d.id,
                    type: DONATION_TYPE_MAP[d.type] || 'blood',
                    title: d.type === 'BLOOD' ? `Urgent ${BLOOD_TYPE_MAP[d.bloodType] || ''} Blood` : d.medicationName,
                    location: 'Casablanca', // Default city
                    time: formatRelativeTime(d.createdAt),
                    status: d.status === 'OPEN' ? 'Open' : 'Fulfilled'
                }));
                if (isMounted) setRecentRequests(mappedData);
            } catch (error) {
                console.error('Error fetching recent donations:', error);
            } finally {
                if (isMounted) setLoading(false);
            }
        };

        fetchRecentRequests();

        return () => {
            isMounted = false;
        };
    }, []);

    return (
        <section className="section">
            <div className="donation-section">
                <div>
                    <div className="section-label">Solidarity</div>
                    <h2 className="section-title">Give, Save<br />Lives</h2>
                    <p className="section-desc">
                        Whether it's blood or medicine, every donation counts. 
                        Find a request near you and take action now.
                    </p>
                    <div style={{ display: 'flex', gap: '12px', marginTop: '28px', flexWrap: 'wrap' }}>
                        <Link to="/donations">
                            <button className="btn-primary" style={{ padding: '12px 22px', fontSize: '14px' }}>
                                <IconDroplet size={15} stroke={2} style={{ marginRight: '6px' }} />
                                Blood Donation
                            </button>
                        </Link>
                        <Link to="/donations">
                            <button className="btn-outline" style={{ padding: '12px 22px', fontSize: '14px' }}>
                                <IconPill size={15} stroke={2} style={{ marginRight: '6px' }} />
                                Medicine Donation
                            </button>
                        </Link>
                    </div>
                </div>
                <div className="donation-visual">
                    <div className="donation-board">
                        <h4>Recent Requests</h4>
                        {loading ? (
                            <p style={{ textAlign: 'center', padding: '20px', color: 'var(--gray-400)' }}>Loading...</p>
                        ) : recentRequests.length > 0 ? (
                            recentRequests.map((request, index) => (
                                <div className="donation-item" key={request.id || index}>
                                    <div className={`d-badge ${request.type}`}>
                                        {request.type === 'blood' ? (
                                            <IconDroplet size={18} stroke={2} aria-hidden="true" />
                                        ) : (
                                            <IconPill size={18} stroke={2} aria-hidden="true" />
                                        )}
                                    </div>
                                    <div className="d-info">
                                        <p>{request.title}</p>
                                        <span>{request.location} · {request.time}</span>
                                    </div>
                                    <span className={`d-status ${request.status === 'Open' ? 'open' : 'done'}`}>
                                        {request.status}
                                    </span>
                                </div>
                            ))
                        ) : (
                            <p style={{ textAlign: 'center', padding: '20px', color: 'var(--gray-400)' }}>No recent requests.</p>
                        )}
                        <Link to="/donations" className="board-link" style={{ display: 'block', textAlign: 'center', marginTop: '16px', fontSize: '13px', color: 'var(--green-600)', textDecoration: 'none', fontWeight: 500 }}>
                            View All Requests →
                        </Link>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default DonationSection;
