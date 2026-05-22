import { useState, useEffect, useMemo } from 'react';
import { Link } from 'react-router-dom';
import {
    IconSearch,
    IconChevronRight,
    IconBook,
    IconBone,
    IconBrain,
    IconHeart,
    IconStethoscope,
    IconStarFilled,
    IconStarHalfFilled,
    IconStar,
    IconLoader2,
    IconDownload,
    IconFilter
} from '@tabler/icons-react';
import api from '../../services/api';
import Navbar from '../../components/Navbar';
import Footer from '../../components/Footer';
import '../../styles/Library.css';

const Library = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [activeCategory, setActiveCategory] = useState('All');

    useEffect(() => {
        let isMounted = true;
        api.get('/books/published')
            .then(res => {
                if (isMounted) { setBooks(res.data); setLoading(false); }
            })
            .catch(() => {
                if (isMounted) { setError('Unable to load books.'); setLoading(false); }
            });
        return () => { isMounted = false; };
    }, []);

    const categories = useMemo(() => {
        const cats = ['All', ...new Set(books.map(b => b.categoryName).filter(Boolean))];
        return cats;
    }, [books]);

    const filteredBooks = useMemo(() => {
        return books.filter(book => {
            const matchesSearch = !searchQuery ||
                book.title?.toLowerCase().includes(searchQuery.toLowerCase()) ||
                book.author?.toLowerCase().includes(searchQuery.toLowerCase());
            const matchesCategory = activeCategory === 'All' || book.categoryName === activeCategory;
            return matchesSearch && matchesCategory;
        });
    }, [books, searchQuery, activeCategory]);

    const getCategoryIcon = (category) => {
        const cat = category?.toLowerCase() || '';
        if (cat.includes('anatom')) return <IconBone size={40} stroke={2} />;
        if (cat.includes('neuro')) return <IconBrain size={40} stroke={2} />;
        if (cat.includes('cardio')) return <IconHeart size={40} stroke={2} />;
        return <IconStethoscope size={40} stroke={2} />;
    };

    const getCoverClass = (index) => {
        const classes = ['c1', 'c2', 'c3', 'c4'];
        return classes[index % 4];
    };

    const renderStars = (rating) => {
        const stars = [];
        const r = rating || 0;
        for (let i = 1; i <= 5; i++) {
            if (i <= Math.floor(r)) {
                stars.push(<IconStarFilled key={i} size={13} />);
            } else if (i === Math.ceil(r) && r % 1 !== 0) {
                stars.push(<IconStarHalfFilled key={i} size={13} />);
            } else {
                stars.push(<IconStar key={i} size={13} />);
            }
        }
        return stars;
    };

    if (loading) {
        return (
            <div className="library-page" style={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <div style={{ textAlign: 'center' }}>
                    <IconLoader2 size={48} className="animate-spin" style={{ color: '#1E7A52' }} />
                    <p style={{ marginTop: 12, color: '#8A9E96' }}>Loading library...</p>
                </div>
            </div>
        );
    }

    return (
        <>
            <Navbar />

            {/* ===== HEADER ===== */}
            <header className="library-header-section">
                <div className="library-header-inner">
                    <div className="library-breadcrumb">
                        <Link to="/">Home</Link>
                        <IconChevronRight size={14} />
                        <span>Library</span>
                    </div>
                    <h1 className="library-title">Medical Library</h1>
                    <p className="library-desc">
                        Browse our curated collection of medical books and academic resources.
                        Search by title, author, or filter by category.
                    </p>

                    {/* Search Bar */}
                    <div className="library-search-bar">
                        <IconSearch size={18} className="library-search-icon" />
                        <input
                            type="text"
                            placeholder="Search books by title or author..."
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            className="library-search-input"
                        />
                    </div>
                </div>
            </header>

            {/* ===== CATEGORY FILTER ===== */}
            <section className="library-filters">
                <div className="library-filters-inner">
                    <div className="filter-label">
                        <IconFilter size={16} />
                        <span>Categories</span>
                    </div>
                    <div className="category-pills">
                        {categories.map(cat => (
                            <button
                                key={cat}
                                className={`category-pill ${activeCategory === cat ? 'active' : ''}`}
                                onClick={() => setActiveCategory(cat)}
                            >
                                {cat}
                            </button>
                        ))}
                    </div>
                </div>
            </section>

            {/* ===== RESULTS ===== */}
            <main className="library-main">
                <div className="library-main-inner">
                    <div className="library-results-header">
                        <span>{filteredBooks.length} {filteredBooks.length === 1 ? 'book' : 'books'} found</span>
                    </div>

                    {error ? (
                        <div className="library-error">{error}</div>
                    ) : filteredBooks.length === 0 ? (
                        <div className="library-empty">
                            <IconBook size={64} style={{ opacity: 0.3 }} />
                            <h3>No books found</h3>
                            <p>Try adjusting your search or filter criteria.</p>
                        </div>
                    ) : (
                        <div className="library-grid">
                            {filteredBooks.map((book, index) => (
                                <div key={book.id || index} className="library-book-card">
                                    <div className={`library-book-cover ${getCoverClass(index)}`}>
                                        {getCategoryIcon(book.categoryName)}
                                        <span className="library-book-badge">{book.categoryName}</span>
                                    </div>
                                    <div className="library-book-info">
                                        <h4 className="library-book-title">{book.title}</h4>
                                        <p className="library-book-author">{book.author} · {book.publishYear}</p>
                                        <div className="library-book-rating">
                                            {renderStars(book.averageRating)}
                                            <span>{book.averageRating?.toFixed(1) || '0.0'}</span>
                                        </div>
                                        {book.downloadCount !== undefined && (
                                            <div className="library-book-downloads">
                                                <IconDownload size={13} />
                                                <span>{book.downloadCount} downloads</span>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </main>

            <Footer />
        </>
    );
};

export default Library;
