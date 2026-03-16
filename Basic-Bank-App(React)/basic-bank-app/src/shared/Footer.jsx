import React from 'react';

const Footer = () => {
  return (
    <footer className="bg-light py-4 mt-auto border-top">
      <div className="container text-center">
        <h5 className="fw-bold mb-1" style={{ color: '#1A237E' }}>VR BANK</h5>
        <p className="text-muted small mb-3">Trusted Digital Banking Partner</p>
        <hr className="w-25 mx-auto" />
        <p className="mb-0 text-secondary" style={{ fontSize: '0.85rem' }}>
          &copy; {new Date().getFullYear()} VR BANK. All rights reserved.
        </p>
        <p className="text-muted" style={{ fontSize: '0.75rem' }}>
          www.vrbank.com
        </p>
      </div>
    </footer>
  );
};

export default Footer;