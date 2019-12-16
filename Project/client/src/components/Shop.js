import React from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import {connect} from 'react-redux';
import * as actions from '../actions';


class Shop extends React.Component {
  async componentDidMount() {
    await this.props.fetchItems().then(this.props.fetchCart());
  };


  renderButton = (id) =>{
    return this.props.auth == false?
    (<Button variant="primary" href="/auth/google">
     Login to add to cart
   </Button>):(<Button 
      variant="primary" 
      id={id} 
      onClick={
      async (e) => await this.props.addToCart(e.target.id)
      }>
      Add to cart
   </Button>)
  }
  

  renderItems = () => {
    return this.props.items == undefined ? "" : this.props.items.map((item) =>
      <Card style={{ width: "100%", justifySelf: "center", marginTop:"20px"}} key={item._id}>
        <Card.Img variant="top" src={item.imageURL} />
        <Card.Body>
          <Card.Title>{item.name}</Card.Title>
          <Card.Text>
            {item.description}
          </Card.Text>
          <Card.Text style={{fontWeight: "bold"}}>
            {item.price+" SEK"}
          </Card.Text>
         {this.renderButton(item._id)}
        </Card.Body>
      </Card>
    );
  }
  render() {
    return (
      <Container style={{marginTop:"3rem"}}>
        <Row >
          <Col></Col>
          <Col sm="5"style={{textAlign: "center"}}>
            {this.renderItems()}
          </Col>
          <Col></Col>
        </Row>
      </Container>
    );
  }
}

function mapStateToProps({items, auth}){
  return{items, auth};
}


export default connect(mapStateToProps, actions)(Shop);
