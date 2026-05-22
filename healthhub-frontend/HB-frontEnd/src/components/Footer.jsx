import { 
    IconStethoscope, 
    IconBrandFacebook, 
    IconBrandInstagram, 
    IconBrandLinkedin 
} from '@tabler/icons-react';

const Footer = () => {
    return (
        <footer className="landing-footer">
            <div className="footer-top">
                <div className="footer-brand">
                    <div className="logo">
                        <div className="logo-icon">
                            <IconStethoscope size={20} stroke={2} aria-hidden="true" />
                        </div>
                        <span className="logo-text" style={{ color: '#fff' }}>Med<span>Library</span></span>
                    </div>
                    <p>Morocco's solidarity medical platform. Knowledge, sharing, and health for all.</p>
                </div>
                <div className="footer-col">
                    <h5>Platform</h5>
                    <ul>
                        <li><a href="#">Library</a></li>
                        <li><a href="#">Donations</a></li>
                        <li><a href="#">Campaigns</a></li>
                        <li><a href="#">Articles</a></li>
                    </ul>
                </div>
                <div className="footer-col">
                    <h5>Account</h5>
                    <ul>
                        <li><a href="#">Login</a></li>
                        <li><a href="#">Register</a></li>
                        <li><a href="#">My Profile</a></li>
                        <li><a href="#">Settings</a></li>
                    </ul>
                </div>
                <div className="footer-col">
                    <h5>Support</h5>
                    <ul>
                        <li><a href="#">About</a></li>
                        <li><a href="#">Contact</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                        <li><a href="#">Terms of Service</a></li>
                    </ul>
                </div>
            </div>
            <div className="footer-bottom">
                <p>© 2025 MedLibrary. All rights reserved.</p>
                <div className="footer-socials">
                    <a href="#" aria-label="Facebook"><IconBrandFacebook size={18} stroke={2} /></a>
                    <a href="#" aria-label="Instagram"><IconBrandInstagram size={18} stroke={2} /></a>
                    <a href="#" aria-label="LinkedIn"><IconBrandLinkedin size={18} stroke={2} /></a>
                </div>
            </div>
        </footer>
    );
};

export default Footer;
