import React, { Component } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Form from 'react-bootstrap/Form';
import FormControl from 'react-bootstrap/FormControl';
import Button from 'react-bootstrap/Button';

class Header extends React.Component {
  render() {
    return (
      <Navbar bg="dark" variant="dark">
        <Navbar.Brand href="/">WEBSHOP</Navbar.Brand>
        <Nav className="mr-auto"></Nav>
        <Nav.Link href="cart">Cart</Nav.Link>
        <Nav style={{color:"white", marginRight: "15px"}}>Items: {this.props.count}</Nav>   
      </Navbar>
    );
  }
}

export default Header;
