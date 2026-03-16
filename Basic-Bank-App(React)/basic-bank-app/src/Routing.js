import React from 'react'
import { Routes, Route } from "react-router-dom";
import LoanCalculator from './components/LoanCalculator';
import DepositCalculator from './components/DepositCalculator';
import Login from './components/Login';
import Home from './components/Home';
import ProtectedRoute from './ProtectedRoute';

function Routing() {
  return (
     <div>
      <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<Home />} />

      {/* Protected Routes - Only accessible if logged in */}
      <Route 
        path="/loan-calculator" 
        element={
          <ProtectedRoute>
            <LoanCalculator />
          </ProtectedRoute>
        } 
      />
      
      <Route 
        path="/deposit-calculator" 
        element={
          <ProtectedRoute>
            <DepositCalculator />
          </ProtectedRoute>
        } 
      />
    </Routes>
    </div>
  )
}


export default Routing