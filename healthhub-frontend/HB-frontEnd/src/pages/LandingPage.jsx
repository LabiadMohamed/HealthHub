import Navbar from '../components/Navbar';
import Hero from '../components/Hero';
import Features from '../components/Features';
import DonationSection from '../components/DonationSection';
import Campaigns from '../components/Campaigns';
import LatestBooks from '../components/LatestBooks';
import CTASection from '../components/CTASection';
import Footer from '../components/Footer';
import '../styles/LandingPage.css';

const LandingPage = () => {
    return (
        <div className="landing-page">
            <Navbar />
            <main>
                <Hero />
                <Features />
                <DonationSection />
                <Campaigns />
                <LatestBooks />
                <CTASection />
            </main>
            <Footer />
        </div>
    );
};

export default LandingPage;
