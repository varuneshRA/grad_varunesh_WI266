import React from 'react';
import Menu from './Menu';

const Header = () => {
  return (
    <nav className="navbar navbar-expand-lg shadow" style={{ backgroundColor: '#1A237E' }}>
      <div className="container">
        {/* Brand */}
        <a className="navbar-brand fw-bold text-white fs-3" href="/">
          VR BANK
        </a>

        {/* Integrated Menu Component */}
        <div className="collapse navbar-collapse justify-content-center">
          <Menu />
        </div>

        {/* Right Side Info */}
        <div className="d-flex align-items-center text-white-50">
          <small><i className="bi bi-shield-check me-1"></i> Secure</small>
        </div>
      </div>
    </nav>
  );
};

export default Header;