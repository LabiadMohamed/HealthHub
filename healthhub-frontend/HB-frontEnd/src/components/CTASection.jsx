import { IconUserPlus, IconInfoCircle } from '@tabler/icons-react';

const CTASection = () => {
    return (
        <section className="cta-section">
            <div className="cta-blob1"></div>
            <div className="cta-blob2"></div>
            <h2>Ready to join<br />the community?</h2>
            <p>Sign up for free and access all medical resources.</p>
            <div className="cta-btns">
                <button className="btn-cta-white">
                    <IconUserPlus size={15} stroke={2} style={{ marginRight: '6px' }} />
                    Create an account
                </button>
                <button className="btn-cta-outline">
                    <IconInfoCircle size={15} stroke={2} style={{ marginRight: '6px' }} />
                    Learn more
                </button>
            </div>
        </section>
    );
};

export default CTASection;
