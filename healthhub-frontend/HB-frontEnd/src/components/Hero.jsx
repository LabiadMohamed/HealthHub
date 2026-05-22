import { 
    IconLeaf, 
    IconBook, 
    IconHeart, 
    IconBook2, 
    IconDroplet, 
    IconUsers 
} from '@tabler/icons-react';

const Hero = () => {
    return (
        <section className="hero">
            <div className="hero-blob"></div>
            <div className="hero-content">
                <div className="hero-badge">
                    <IconLeaf size={13} stroke={2} style={{ marginRight: '6px' }} />
                    Medical & Solidarity Platform
                </div>
                <h1>Healthcare,<br />accessible to <em>everyone</em></h1>
                <p className="hero-desc">
                    Medical library, blood and medicine donations, awareness campaigns — 
                    all in one place for your health and your community.
                </p>
                <div className="hero-cta">
                    <button className="btn-hero btn-hero-primary">
                        <IconBook size={15} stroke={2} style={{ marginRight: '6px' }} />
                        Explore Library
                    </button>
                    <button className="btn-hero btn-hero-secondary">
                        <IconHeart size={15} stroke={2} style={{ marginRight: '6px' }} />
                        Make a Donation
                    </button>
                </div>
                <div className="hero-stats">
                    <div className="stat-item">
                        <div className="stat-num">1,200+</div>
                        <div className="stat-label">Medical Books</div>
                    </div>
                    <div className="stat-item">
                        <div className="stat-num">850+</div>
                        <div className="stat-label">Active Donors</div>
                    </div>
                    <div className="stat-item">
                        <div className="stat-num">40+</div>
                        <div className="stat-label">Active Campaigns</div>
                    </div>
                </div>
            </div>
            <div className="hero-visual">
                <div className="float-card">
                    <div className="card-icon g1">
                        <IconBook2 size={20} stroke={2} aria-hidden="true" />
                    </div>
                    <div className="card-info">
                        <p>Human Anatomy</p>
                        <span>Downloaded 342 times</span>
                    </div>
                </div>
                <div className="float-card">
                    <div className="card-icon g2">
                        <IconDroplet size={20} stroke={2} aria-hidden="true" />
                    </div>
                    <div className="card-info">
                        <p>Blood Donation — O+</p>
                        <span>Urgent Request · Casablanca</span>
                    </div>
                </div>
                <div className="float-card">
                    <div className="card-icon g3">
                        <IconUsers size={20} stroke={2} aria-hidden="true" />
                    </div>
                    <div className="card-info">
                        <p>Ramadan Campaign</p>
                        <span>127 volunteers registered</span>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Hero;
