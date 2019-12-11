import React from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import axios from 'axios';

class CurrencyForm extends React.Component {
  state = {
    currencies: [],
    from: "",
    to:"",
    amount: 0
  }
  componentDidMount(){
   this.fetchCurrencies();
  }

  fetchCurrencies = async () => {
    const response = await axios.get('http://localhost:8080/api/currency/all')
    const currencyIds = response.data.map(currency=>currency.id);
    this.setState({currencies: [...this.state.currencies,...currencyIds]})
    console.log(this.state)
  }

  handleChange(e) {
   // this.setState({ [e.target.name] : e.target.value });
 }

  render() {
    const items = this.state.currencies.map(currency => <option>{currency}</option>)
    return (
      <div style={{ width: "50%", textAlign: "center", marginLeft: "25%", marginTop:"15%"}}>
        <Form>
          <Form.Group controlId="selectFrom">
            <Form.Label>Currency From</Form.Label>
            <Form.Control as="select" value={this.state.from} name="from" onChange={this.handleChange("from")}>
              {items}
            </Form.Control>
          </Form.Group>
          <Form.Group controlId="selectTo">
            <Form.Label>Currecy To</Form.Label>
            <Form.Control as="select" value={this.state.to} name="from" onChange={this.handleChange("to")}>
              {items}
            </Form.Control>
          </Form.Group>
          <Form.Group controlId="">
            <Form.Label>Amount</Form.Label>
            <Form.Control type="text" placeholder="Amount" name="amount" value={this.state.amount} onChange={this.handleChange("amount")}/>
          </Form.Group>
          <Button variant="primary" type="submit">
            Convert
          </Button>
        </Form>
      </div>
    );
  }

 
  
}


export default CurrencyForm;
