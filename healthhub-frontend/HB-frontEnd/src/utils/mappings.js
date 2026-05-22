/**
 * Utility functions for mapping backend enums to frontend display formats
 */

export const BLOOD_TYPE_MAP = {
    'A_POS': 'A+',
    'A_NEG': 'A-',
    'B_POS': 'B+',
    'B_NEG': 'B-',
    'AB_POS': 'AB+',
    'AB_NEG': 'AB-',
    'O_POS': 'O+',
    'O_NEG': 'O-',
};

export const REVERSE_BLOOD_TYPE_MAP = Object.fromEntries(
    Object.entries(BLOOD_TYPE_MAP).map(([k, v]) => [v, k])
);

export const DONATION_TYPE_MAP = {
    'BLOOD': 'blood',
    'MEDICATION': 'med',
    'MONEY': 'money',
};

export const REVERSE_DONATION_TYPE_MAP = {
    'blood': 'BLOOD',
    'med': 'MEDICATION',
    'money': 'MONEY',
};

export const DONATION_STATUS_MAP = {
    'OPEN': 'open',
    'FULFILLED': 'done',
    'CANCELLED': 'cancelled',
};

/**
 * Format relative time (e.g., "2h ago") from a LocalDateTime string
 */
export const formatRelativeTime = (dateString) => {
    if (!dateString) return 'Recently';
    const date = new Date(dateString);
    const now = new Date();
    const diffInSeconds = Math.floor((now - date) / 1000);
    
    if (diffInSeconds < 60) return 'Just now';
    if (diffInSeconds < 3600) return `${Math.floor(diffInSeconds / 60)}m ago`;
    if (diffInSeconds < 86400) return `${Math.floor(diffInSeconds / 3600)}h ago`;
    if (diffInSeconds < 604800) return `${Math.floor(diffInSeconds / 86400)}d ago`;
    
    return date.toLocaleDateString('en-US');
};
