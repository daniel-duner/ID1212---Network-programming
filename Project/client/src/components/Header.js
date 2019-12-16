import React, { Component, Fragment } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Form from 'react-bootstrap/Form';
import FormControl from 'react-bootstrap/FormControl';
import Button from 'react-bootstrap/Button';
import {connect} from 'react-redux';

class Header extends React.Component {
    quantityOfItems = ()=>{
        const length = this.props.cart.length;
        let quantity = 0;
        for(let i = 0; i < length; i++){
            quantity = quantity+(this.props.cart[i].quantity);
        }
        return quantity;
    }
    renderContent(){
        switch(this.props.auth){
            case null:
                return 'Loading...';
            case false:
                return <Button variant="primary" href="/auth/google">Login with Google</Button>;
            default:
                return (
                    <Fragment>
                        <Nav.Link href="cart">Cart</Nav.Link>
                        <Nav style={{color:"white", marginRight: "15px"}}>Items: {this.props.cart != null?this.quantityOfItems():0}</Nav>
                        <Button variant="primary" href="/api/logout">Logout</Button>
                    </Fragment>
                );
        }
    }
    render() {
    return (
      <Navbar fixed="top" bg="dark" variant="dark">
        <Navbar.Brand href="/">WEBSHOP</Navbar.Brand>
        <Nav className="mr-auto"></Nav>
        {this.renderContent()}
      </Navbar>
    );
  }
}

function mapStateToProps({auth, cart}){
    return{auth,cart};
}

export default connect(mapStateToProps)(Header);
