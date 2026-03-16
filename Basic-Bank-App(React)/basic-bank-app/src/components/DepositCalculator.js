import React, { useState, useEffect } from 'react';

const DepositCalculator = () => {
  const [amount, setAmount] = useState(10000);
  const [scheme, setScheme] = useState('FD');
  const [tenure, setTenure] = useState(1); // Years

  const [results, setResults] = useState({
    maturityAmount: 0,
    totalInterest: 0,
    rate: 7.5
  });

  // Business Logic: Interest Rates based on Scheme
  const getRate = (type) => {
    const rates = {
      'FD': 7.5,        // Fixed Deposit
      'RD': 6.8,        // Recurring Deposit
      'TAX': 8.2        // Tax Saver
    };
    return rates[type] || 7.0;
  };

  // Calculate whenever inputs change
  useEffect(() => {
    const rate = getRate(scheme);
    const p = parseFloat(amount) || 0;
    const t = parseFloat(tenure) || 0;
    const r = rate / 100;

    let maturity = 0;

    if (scheme === 'RD') {
      // RD Formula: M = P * ((1+r)^n - 1) / (1 - (1+r)^(-1/3)) 
      // Simplified approximation for monthly compounding
      const monthlyP = p;
      const totalMonths = t * 12;
      const i = rate / 1200;
      maturity = monthlyP * (((Math.pow(1 + i, totalMonths) - 1) / i) * (1 + i));
    } else {
      // Standard FD Compound Interest (Quarterly)
      const n = 4; // Quarterly
      maturity = p * Math.pow((1 + r / n), (n * t));
    }

    setResults({
      maturityAmount: maturity,
      totalInterest: maturity - (scheme === 'RD' ? p * t * 12 : p),
      rate: rate
    });
  }, [amount, scheme, tenure]);

  const formatCurrency = (val) => {
    return "₹ " + val.toLocaleString('en-IN', { 
      maximumFractionDigits: 0 
    });
  };

  function resetFields() {
    setAmount(10000);
    setScheme('FD');
    setTenure(1);
  }

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6 col-lg-5">
          <div className="card shadow-lg border-0 rounded-4 overflow-hidden">
            {/* Header */}
            <div className="p-4 text-center text-white" style={{ background: '#1A237E' }}>
              <i className="bi bi-piggy-bank fs-1 mb-2"></i>
              <h3 className="fw-bold m-0">Deposit Calculator</h3>
              <p className="small opacity-75 mb-0">Grow your wealth with VR BANK</p>
            </div>

            <div className="card-body p-4 bg-white">
              {/* Scheme Selection */}
              <div className="mb-3">
                <label className="form-label small fw-bold text-muted">INVESTMENT SCHEME</label>
                <select 
                  className="form-select bg-light border-0 py-2"
                  value={scheme}
                  onChange={(e) => setScheme(e.target.value)}
                >
                  <option value="FD">Fixed Deposit (7.5%)</option>
                  <option value="RD">Recurring Deposit (6.8%)</option>
                  <option value="TAX">Tax Saver Deposit (8.2%)</option>
                </select>
              </div>

              {/* Amount Input */}
              <div className="mb-3">
                <label className="form-label small fw-bold text-muted">
                  {scheme === 'RD' ? 'MONTHLY INSTALLMENT (₹)' : 'DEPOSIT AMOUNT (₹)'}
                </label>
                <input 
                  type="number" 
                  className="form-control bg-light border-0 py-2" 
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  placeholder="Min. 1000"
                />
              </div>

              {/* Tenure Slider */}
              <div className="mb-4">
                <label className="form-label small fw-bold text-muted d-flex justify-content-between">
                  TENURE <span>{tenure} Years</span>
                </label>
                <input 
                  type="range" 
                  className="form-range" 
                  min="1" max="10" step="1"
                  value={tenure}
                  onChange={(e) => setTenure(e.target.value)}
                />
              </div>

              {/* Results Summary */}
              <div className="rounded-3 p-3 mb-4" style={{ background: '#F8F9FA', borderLeft: '5px solid #1A237E' }}>
                <div className="d-flex justify-content-between mb-2">
                  <span className="text-muted">Maturity Value:</span>
                  <span className="fw-bold text-primary fs-5">{formatCurrency(results.maturityAmount)}</span>
                </div>
                <div className="d-flex justify-content-between small">
                  <span className="text-muted">Interest Earned:</span>
                  <span className="text-success fw-bold">+{formatCurrency(results.totalInterest)}</span>
                </div>
              </div>

              <div className="d-grid gap-2">
                <button className="btn btn-primary fw-bold py-2 rounded-pill shadow-sm">
                  INVEST NOW
                </button>
                <button className="btn btn-link btn-sm text-decoration-none text-muted" onClick={resetFields}>
                  Reset Fields
                </button>
              </div>
            </div>
          </div>
          
          <p className="text-center mt-3 text-muted small">
            <i className="bi bi-info-circle me-1"></i> 
            Interest compounded quarterly. Rates subject to change.
          </p>
        </div>
      </div>
    </div>
  );
};

export default DepositCalculator;