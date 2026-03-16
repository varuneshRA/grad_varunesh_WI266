import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate, Link } from 'react-router-dom';
import { loginAction } from '../reduxContainer/AuthAction';

const Login = () => {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

    // Standard Bank Admin Credentials
    if (userId === "admin" && password === "admin") {
      const userData = { 
        id: userId, 
        loginTime: new Date().toLocaleTimeString(),
        role: 'Premium User' 
      };

      // Update Redux state to isLoggedIn: true
      dispatch(loginAction(userData));

      alert(`Welcome back, ${userId}! Secure Access Granted.`);
      
      // Redirect to the protected route
      navigate('/loan-calculator');
    } else {
      alert("Invalid User ID or Password. Please try again.");
    }
  };

  return (
    <div className="row justify-content-center py-5">
      <div className="col-md-5 col-lg-4">
        <div className="card border-0 shadow-lg rounded-4 overflow-hidden">
          {/* Top Decorative Strip */}
          <div className="py-2" style={{ backgroundColor: '#1A237E' }}></div>
          
          <div className="card-body p-5">
            <div className="text-center mb-4">
              <i className="bi bi-person-lock fs-1" style={{ color: '#1A237E' }}></i>
              <h3 className="fw-bold mt-2">Secure Login</h3>
              <p className="text-muted small">Access your VR BANK account</p>
            </div>

            <form onSubmit={handleLogin}>
              <div className="mb-3">
                <label className="form-label small fw-bold text-secondary">USER ID</label>
                <div className="input-group">
                  <span className="input-group-text bg-light border-0">
                    <i className="bi bi-person text-muted"></i>
                  </span>
                  <input 
                    type="text" 
                    className="form-control bg-light border-0 shadow-none" 
                    placeholder="Enter Username"
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                    required
                  />
                </div>
              </div>

              <div className="mb-4">
                <label className="form-label small fw-bold text-secondary">PASSWORD</label>
                <div className="input-group">
                  <span className="input-group-text bg-light border-0">
                    <i className="bi bi-key text-muted"></i>
                  </span>
                  <input 
                    type="password" 
                    className="form-control bg-light border-0 shadow-none" 
                    placeholder="••••••••"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
              </div>

              <button 
                type="submit" 
                className="btn btn-primary w-100 py-2 fw-bold rounded-pill shadow-sm"
                style={{ backgroundColor: '#1A237E', border: 'none' }}
              >
                SIGN IN
              </button>
            </form>

            <div className="mt-4 text-center">
              {/* Fixed ESLint Warning: Used button instead of <a> for action */}
              <button 
                type="button" 
                className="btn btn-link btn-sm text-decoration-none text-muted p-0"
                onClick={() => alert("Please contact support to reset your password.")}
              >
                Forgot Password?
              </button>
              
              <hr className="my-3" />
              
              <p className="small text-muted mb-1">Don't have an account?</p>
              
              {/* Fixed ESLint Warning: Used Link for navigation */}
              <Link 
                to="/" 
                className="fw-bold text-decoration-none" 
                style={{ color: '#1A237E' }}
              >
                Open an Account
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;