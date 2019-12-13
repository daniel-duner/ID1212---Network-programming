import React from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import axios from 'axios';


class Shop extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.getItems();
  };
  
  async getItems(){
    const response = await axios.get("/api/item/getAll");
    this.props.setItems(response.data);
  };

  addToCart = e =>{
    this.props.addToCart(e.target.name, e.target.price);
  }

  renderItems = () => {
    return this.props.items == [] ? 0 : this.props.items.map((item, index) =>
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
          <Button variant="primary" name={item.name} price={item.price} onClick={this.addToCart}>Add to cart</Button>
        </Card.Body>
      </Card>
    );
  }
  render() {
    return (
      <Container>
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

export default Shop;
