import React from 'react';
import { NavLink } from "react-router-dom";

function Menu() {
  // Style for the active link
  const activeLink = ({ isActive }) => ({
    color: isActive ? '#fff' : 'rgba(255,255,255,0.7)',
    fontWeight: isActive ? 'bold' : 'normal',
    textDecoration: 'none'
  });

  return (
    <div className="d-flex gap-4">
      <NavLink to="/" style={activeLink}>Home</NavLink>
      <NavLink to="/loan-calculator" style={activeLink}>Loan</NavLink>
      <NavLink to="/deposit-calculator" style={activeLink}>Deposit</NavLink>
      <NavLink to="/login" style={activeLink}>Login</NavLink>
    </div>
  );
}

export default Menu;