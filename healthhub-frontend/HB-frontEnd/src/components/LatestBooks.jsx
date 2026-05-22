import { useState, useEffect } from 'react';
import api from '../services/api';
import { 
    IconArrowRight, 
    IconBone, 
    IconBrain, 
    IconHeart, 
    IconStethoscope, 
    IconStarFilled, 
    IconStarHalfFilled, 
    IconStar,
    IconBook
} from '@tabler/icons-react';

const LatestBooks = () => {
    const [booksData, setBooksData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        let isMounted = true;

        const loadBooks = async () => {
            try {
                const response = await api.get('/books/published');
                if (isMounted) {
                    setBooksData(response.data);
                    setLoading(false);
                }
            } catch (error) {
                console.error('Error fetching books:', error);
                if (isMounted) setLoading(false);
            }
        };

        loadBooks();

        return () => {
            isMounted = false;
        };
    }, []);

    const renderStars = (rating) => {
        const stars = [];
        const roundedRating = rating || 0;
        for (let i = 1; i <= 5; i++) {
            if (i <= Math.floor(roundedRating)) {
                stars.push(<IconStarFilled key={i} size={12} />);
            } else if (i === Math.ceil(roundedRating) && roundedRating % 1 !== 0) {
                stars.push(<IconStarHalfFilled key={i} size={12} />);
            } else {
                stars.push(<IconStar key={i} size={12} />);
            }
        }
        return stars;
    };

    const getCategoryIcon = (category) => {
        const cat = category?.toLowerCase() || '';
        if (cat.includes('anatomie')) return <IconBone size={40} stroke={2} />;
        if (cat.includes('neuro')) return <IconBrain size={40} stroke={2} />;
        if (cat.includes('cardio')) return <IconHeart size={40} stroke={2} />;
        return <IconStethoscope size={40} stroke={2} />;
    };

    const getCoverClass = (index) => {
        const classes = ['c1', 'c2', 'c3', 'c4'];
        return classes[index % 4];
    };

    return (
        <section className="section library-section">
            <div className="library-header">
                <div>
                    <div className="section-label">Medical Knowledge</div>
                    <h2 className="section-title">Latest Publications</h2>
                </div>
                <button className="btn-outline" style={{ padding: '10px 20px', fontSize: '14px' }}>
                    View All <IconArrowRight size={14} style={{ marginLeft: '6px', verticalAlign: '-2px' }} />
                </button>
            </div>
            <div className="books-grid">
                {loading ? (
                    <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '40px' }}>Loading...</div>
                ) : booksData.length > 0 ? (
                    booksData.map((book, index) => (
                        <div className="book-card" key={book.id || index}>
                            <div className={`book-cover ${getCoverClass(index)}`}>
                                {getCategoryIcon(book.categoryName)}
                                <span className="book-badge">{book.categoryName}</span>
                            </div>
                            <div className="book-info">
                                <h4>{book.title}</h4>
                                <span>{book.author} · {book.publishYear}</span>
                                <div className="book-rating">
                                    {renderStars(book.averageRating)}
                                    <span>{book.averageRating?.toFixed(1) || '0.0'}</span>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '40px', color: 'var(--gray-400)' }}>
                        <IconBook size={48} style={{ display: 'block', margin: '0 auto 16px', opacity: '.4' }} />
                        <p>No books available at the moment.</p>
                    </div>
                )}
            </div>
        </section>
    );
};

export default LatestBooks;
