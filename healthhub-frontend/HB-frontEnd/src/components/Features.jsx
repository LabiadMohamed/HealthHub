import { 
    IconBooks, 
    IconDropletFilled, 
    IconPill, 
    IconSpeakerphone, 
    IconNews, 
    IconBell 
} from '@tabler/icons-react';

const Features = () => {
    const featuresData = [
        {
            icon: <IconBooks size={24} stroke={2} />,
            title: "Medical Library",
            description: "Access thousands of medical books and documents, categorized and downloadable in PDF."
        },
        {
            icon: <IconDropletFilled size={24} stroke={2} />,
            title: "Blood Donations",
            description: "Post or respond to urgent blood donation requests based on your blood type and location."
        },
        {
            icon: <IconPill size={24} stroke={2} />,
            title: "Medicine Donations",
            description: "Share or request medicines you need. Simple, fast, and community-driven."
        },
        {
            icon: <IconSpeakerphone size={24} stroke={2} />,
            title: "Health Campaigns",
            description: "Join or create medical awareness campaigns and mobilize volunteers."
        },
        {
            icon: <IconNews size={24} stroke={2} />,
            title: "Articles & News",
            description: "Stay informed with medical articles written by health professionals."
        },
        {
            icon: <IconBell size={24} stroke={2} />,
            title: "Personalized Notifications",
            description: "Receive alerts for urgent donations, campaigns, and new publications relevant to you."
        }
    ];

    return (
        <section className="section features">
            <div className="features-header">
                <div className="section-label">What We Offer</div>
                <h2 className="section-title">A Comprehensive Platform<br />for Your Health</h2>
                <p className="section-desc">Everything you need to access medical knowledge and help your community.</p>
            </div>
            <div className="features-grid">
                {featuresData.map((feature, index) => (
                    <div className="feature-card" key={index}>
                        <div className="feature-icon">
                            {feature.icon}
                        </div>
                        <h3>{feature.title}</h3>
                        <p>{feature.description}</p>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default Features;
