import { useState, useEffect } from 'react';
import api from '../services/api';
import { IconHeart, IconVaccine, IconBook, IconUsers } from '@tabler/icons-react';

const Campaigns = () => {
    const [campaignsData, setCampaignsData] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchCampaigns = async (isMounted = true) => {
        try {
            const response = await api.get('/campaigns/active');
            if (isMounted) setCampaignsData(response.data);
        } catch (error) {
            console.error('Error fetching campaigns:', error);
        } finally {
            if (isMounted) setLoading(false);
        }
    };

    useEffect(() => {
        let isMounted = true;
        // Wrapping in Promise.resolve() ensures this call is truly async to the effect body
        Promise.resolve().then(() => fetchCampaigns(isMounted));
        return () => {
            isMounted = false;
        };
    }, []);

    const calculateProgress = (current, target) => {
        if (!target) return 0;
        return Math.min(Math.round((current / target) * 100), 100);
    };

    const getCampaignIcon = (title) => {
        const t = title.toLowerCase();
        if (t.includes('don') || t.includes('sang')) return <IconHeart size={32} stroke={2} />;
        if (t.includes('santé') || t.includes('vaccin')) return <IconVaccine size={32} stroke={2} />;
        return <IconBook size={32} stroke={2} />;
    };

    const getCampaignBg = (index) => {
        const gradients = [
            "linear-gradient(135deg,#1E7A52,#3DAD7A)",
            "linear-gradient(135deg,#155C3D,#1E7A52)",
            "linear-gradient(135deg,#0D3D27,#155C3D)"
        ];
        return gradients[index % 3];
    };

    const formatDateRange = (start, end) => {
        if (!start) return 'Ongoing';
        const s = new Date(start).toLocaleDateString('en-US', { day: 'numeric', month: 'long' });
        if (!end) return `From ${s}`;
        const e = new Date(end).toLocaleDateString('en-US', { day: 'numeric', month: 'long', year: 'numeric' });
        return `${s} – ${e}`;
    };

    const handleJoin = async (id) => {
        try {
            await api.post(`/campaigns/${id}/join`);
            fetchCampaigns(); // Refresh to update participant count
        } catch (error) {
            console.error('Error joining campaign:', error);
        }
    };

    return (
        <section className="section campaigns">
            <div className="campaigns-header">
                <div className="section-label">Engagement</div>
                <h2 className="section-title">Ongoing Campaigns</h2>
                <p className="section-desc" style={{ margin: '0 auto' }}>Join a campaign and be part of the change.</p>
            </div>
            <div className="campaigns-grid">
                {loading ? (
                    <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '40px' }}>Loading...</div>
                ) : campaignsData.length > 0 ? (
                    campaignsData.map((campaign, index) => {
                        const progress = calculateProgress(campaign.currentParticipants, campaign.targetParticipants);
                        return (
                            <div className="campaign-card" key={campaign.id || index}>
                                <div className="campaign-top" style={{ background: getCampaignBg(index) }}>
                                    <div style={{ position: 'absolute', right: '20px', top: '20px', opacity: 0.3 }}>
                                        {getCampaignIcon(campaign.title)}
                                    </div>
                                    <h3>{campaign.title}</h3>
                                    <span>{formatDateRange(campaign.startDate, campaign.endDate)}</span>
                                </div>
                                <div className="campaign-body">
                                    <p style={{ fontSize: '13px', color: 'var(--gray-600)', fontWeight: 300 }}>
                                        {campaign.description}
                                    </p>
                                    <div className="progress-bar">
                                        <div className="progress-fill" style={{ width: `${progress}%` }}></div>
                                    </div>
                                    <div className="campaign-meta">
                                        <span>{progress}% of goal</span>
                                        <span>{campaign.currentParticipants} participants</span>
                                    </div>
                                    <button className="join-btn" onClick={() => handleJoin(campaign.id)}>Join</button>
                                </div>
                            </div>
                        );
                    })
                ) : (
                    <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '40px', color: 'var(--gray-400)' }}>
                        <IconUsers size={48} style={{ display: 'block', margin: '0 auto 16px', opacity: '.4' }} />
                        <p>No active campaigns at the moment.</p>
                    </div>
                )}
            </div>
        </section>
    );
};

export default Campaigns;
