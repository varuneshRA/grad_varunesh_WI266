import React from 'react';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className="animate-fade-in">
      {/* Hero Section */}
      <div className="row align-items-center py-5">
        <div className="col-md-6">
          <h1 className="display-3 fw-bold mb-4" style={{ color: '#1A237E' }}>
            Smart Banking <br /> for a Digital World.
          </h1>
          <p className="lead text-muted mb-5">
            Experience the next generation of secure financial services. 
            From instant loans to high-yield deposits, VR BANK is your 
            trusted partner since 1990.
          </p>
          <div className="d-flex gap-3">
            <button 
              className="btn btn-lg px-5 py-3 text-white shadow-sm rounded-pill" 
              style={{ backgroundColor: '#1A237E' }}
              onClick={() => navigate('/login')}
            >
              Get Started
            </button>
            <button className="btn btn-outline-secondary btn-lg px-5 py-3 rounded-pill">
              Learn More
            </button>
          </div>
        </div>

        {/* Visual Element */}
        <div className="col-md-6 d-none d-md-block text-center">
          <div className="p-5">
            <i className="bi bi-bank2" style={{ fontSize: '10rem', color: '#E0E0E0' }}></i>
          </div>
        </div>
      </div>

      {/* Feature Quick-View */}
      <div className="row g-4 mt-5">
        <div className="col-md-4">
          <div className="card border-0 shadow-sm p-4 text-center">
            <i className="bi bi-shield-check fs-1 text-success mb-3"></i>
            <h5>Secure SSL</h5>
            <p className="small text-muted">256-bit encryption for every transaction.</p>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card border-0 shadow-sm p-4 text-center">
            <i className="bi bi-lightning-charge fs-1 text-warning mb-3"></i>
            <h5>Instant Approval</h5>
            <p className="small text-muted">Fast processing for loan applications.</p>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card border-0 shadow-sm p-4 text-center">
            <i className="bi bi-headset fs-1 text-primary mb-3"></i>
            <h5>24/7 Support</h5>
            <p className="small text-muted">Dedicated helpdesk for all your queries.</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;