import React, { Component, Fragment } from "react";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import * as actions from "../actions";

class Cart extends React.Component {

  renderCard = item => {
    return (
      <Card
        key={item._id}
        style={{
          width: "100%",
          justifySelf: "center",
          textAlign: "center",
          marginTop: "20px"
        }}
      >
        <Row>
          <Col sm="3">
            <Card.Img
              style={{ width: "200px", height: "auto" }}
              variant="top"
              src={`${item.imageURL}`}
            />
          </Col>
          <Col sm="7">
            <Card.Body>
              <Card.Title>{item.name}</Card.Title>
              <Card.Text style={{ fontWeight: "bold" }}>{new Intl.NumberFormat('de-DE',{style: 'currency', currency: 'SEK'}).format(item.price*item.quantity)}</Card.Text>
              <Card.Text>
                {"Amount: " + item.quantity}
              </Card.Text>
              <Button
                variant="danger"
                id={item._id}
                onClick={ async (e) => await this.props.removeItemFromCart(e.target.id)
                }
              >
                Remove Item
              </Button>
            </Card.Body>
          </Col>
        </Row>
      </Card>
    );
  };

  renderRemove = (val) =>{
    return (val == 0) ? <Button style={{ margin: " 0 10px 0 10px" }} variant="danger" onClick={this.props.removeAllItemsFromCart} disabled>Remove All</Button>
        :
    <Button style={{ margin: " 0 10px 0 10px" }} variant="danger" onClick={this.props.removeAllItemsFromCart}>Remove All</Button>
  }

  renderPrice = () => {
    let value = 0;
    if(this.props.cart != null){
        this.props.cart.map(item => (value = value + parseInt(item.price) * parseInt(item.quantity)));
    }
    return (
      <Card
        style={{
          width: "100%",
          justifySelf: "center",
          textAlign: "center",
          marginTop: "20px"
        }}
      >
        <Card.Body>
          <Card.Title>Total Price</Card.Title>
          <Card.Text style={{ fontWeight: "bold" }}>{new Intl.NumberFormat('de-DE',{style: 'currency', currency: 'SEK'}).format(value)}</Card.Text>
          {this.renderRemove(value)}
          <Button variant="success" disabled>Buy Now</Button>
        </Card.Body>
      </Card>
    );
  };

  render() {
    return (
      <Container style={{ marginTop: "4.5rem" }}>
        {console.log("what this",this.props.cart)}
        {this.props.cart != null? this.props.cart.map(item => this.renderCard(item)):""}
        {this.renderPrice()}
      </Container>
    );
  }
}

const mapStateToProps = ({ auth, cart }) => {
  return { auth, cart };
};

export default connect(mapStateToProps, actions)(Cart);
