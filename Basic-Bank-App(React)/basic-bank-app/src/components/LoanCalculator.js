import React, { useState } from 'react';

const LoanCalculator = () => {
  // State for Form Inputs
  const [loanData, setLoanData] = useState({
    applicant: '',
    loanType: '',
    interest: '',
    duration: '',
    amount: ''
  });

  // State for Results
  const [results, setResults] = useState({
    emi: 0,
    totalInterest: 0,
    totalPayment: 0
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    
    // Automatically update interest rate based on loan type
    if (name === 'loanType') {
      const rates = { HOME: 9, CAR: 12, PERSONAL: 15 };
      setLoanData(prev => ({
        ...prev,
        loanType: value,
        interest: rates[value] || ""
      }));
    } else {
      setLoanData(prev => ({ ...prev, [name]: value }));
    }
  };

  const calculateEMI = () => {
    const { applicant, interest, amount, duration } = loanData;
    const p = parseFloat(amount);
    const rVal = parseFloat(interest);
    const t = parseFloat(duration);

    if (applicant.length < 5 || isNaN(p) || isNaN(t) || isNaN(rVal)) {
      alert("Please check your inputs. Name must be at least 5 characters.");
      return;
    }

    // Logic for specific types
    if (loanData.loanType === "HOME" && amount < 500000) { alert("Min Home Loan amount is ₹5,00,000"); return; }
    if (loanData.loanType === "CAR" && amount < 100000) { alert("Min Car Loan amount is ₹1,00,000"); return; }
    if (loanData.loanType === "PERSONAL" && amount < 50000) { alert("Min Personal Loan amount is ₹50,000"); return; }

    if (loanData.loanType === "HOME" && duration > 30) { alert("Max Home Loan tenure is 30 years"); return; }
    if (loanData.loanType === "CAR" && duration > 10) { alert("Max Car Loan tenure is 10 years"); return; }
    if (loanData.loanType === "PERSONAL" && duration > 5) { alert("Max Personal Loan tenure is 5 years"); return; }

    const monthlyRate = rVal / (12 * 100);
    const months = t * 12;
    
    // EMI Calculation Formula
    const emiValue = (p * monthlyRate * Math.pow(1 + monthlyRate, months)) / (Math.pow(1 + monthlyRate, months) - 1);
    const totalPay = emiValue * months;
    const totalInt = totalPay - p;

    setResults({
      emi: emiValue,
      totalInterest: totalInt,
      totalPayment: totalPay
    });
  };

  const formatCurrency = (val) => {
    return "₹ " + val.toLocaleString('en-IN', { maximumFractionDigits: 2 });
  };

  return (
    <div className="container mt-5">
      <div className="card shadow-lg p-4 p-md-5 border-0 rounded-4">
        <div className="row g-5">
          {/* Form Section */}
          <div className="col-md-6 border-end">
            <h4 className="fw-bold text-primary mb-4">
              <i className="bi bi-calculator me-2"></i>Calculate EMI
            </h4>
            <div className="mb-3">
              <label className="form-label small fw-bold text-muted">APPLICANT NAME</label>
              <input 
                type="text" 
                name="applicant"
                className="form-control bg-light py-2 shadow-sm" 
                placeholder="Full Name"
                value={loanData.applicant}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label className="form-label small fw-bold text-muted">LOAN CATEGORY</label>
              <select 
                name="loanType" 
                className="form-select bg-light py-2 shadow-sm"
                value={loanData.loanType}
                onChange={handleInputChange}
              >
                <option value="">-- Select Category --</option>
                <option value="HOME">HOME LOAN (9%)</option>
                <option value="CAR">CAR LOAN (12%)</option>
                <option value="PERSONAL">PERSONAL LOAN (15%)</option>
              </select>
            </div>
            <div className="row mb-3 g-2">
              <div className="col-6">
                <label className="form-label small fw-bold text-muted">RATE (%)</label>
                <input 
                  type="text" 
                  className="form-control bg-white" 
                  readOnly 
                  value={loanData.interest}
                />
              </div>
              <div className="col-6">
                <label className="form-label small fw-bold text-muted">TENURE (YRS)</label>
                <input 
                  type="number" 
                  name="duration"
                  className="form-control bg-light shadow-sm" 
                  placeholder="Years"
                  value={loanData.duration}
                  onChange={handleInputChange}
                />
              </div>
            </div>
            <div className="mb-4">
              <label className="form-label small fw-bold text-muted">LOAN AMOUNT (₹)</label>
              <input 
                type="number" 
                name="amount"
                className="form-control bg-light py-2 shadow-sm" 
                placeholder="e.g. 500000"
                value={loanData.amount}
                onChange={handleInputChange}
              />
            </div>
            <div className="d-grid">
              <button 
                type="button" 
                className="btn btn-primary py-2 fw-bold rounded-pill" 
                onClick={calculateEMI}
              >
                CALCULATE NOW
              </button>
            </div>
          </div>

          {/* Result Section */}
          <div className="col-md-6 text-center d-flex flex-column justify-content-center">
            <div 
              className="p-4 rounded-circle bg-success bg-opacity-10 border border-success border-opacity-25 mx-auto mb-4 d-flex flex-column justify-content-center" 
              style={{ width: '220px', height: '220px' }}
            >
              <h6 className="text-success fw-bold small mb-1">MONTHLY EMI</h6>
              <h2 className="text-success fw-bold mb-0">
                {formatCurrency(results.emi)}
              </h2>
            </div>
            <div className="bg-light p-3 rounded-3 text-start small border shadow-sm">
              <div className="d-flex justify-content-between mb-2">
                <span className="text-muted">Total Interest:</span>
                <span className="fw-bold">{formatCurrency(results.totalInterest)}</span>
              </div>
              <div className="d-flex justify-content-between">
                <span className="text-muted">Total Repayment:</span>
                <span className="fw-bold">{formatCurrency(results.totalPayment)}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoanCalculator;