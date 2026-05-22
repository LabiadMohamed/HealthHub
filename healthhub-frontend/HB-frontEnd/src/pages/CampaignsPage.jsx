import Navbar from '../components/Navbar';
import Campaigns from '../components/Campaigns';
import Footer from '../components/Footer';
import '../styles/LandingPage.css';

const CampaignsPage = () => {
    return (
        <div className="landing-page">
            <Navbar />
            <main style={{ paddingTop: '70px' }}>
                <Campaigns />
            </main>
            <Footer />
        </div>
    );
};

export default CampaignsPage;
