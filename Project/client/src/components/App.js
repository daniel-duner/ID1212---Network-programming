import React from 'react';
import Shop from './Shop';
import Header from './Header';
import Home from './Home';
import { BrowserRouter, Route } from "react-router-dom";
import axios from 'axios';


class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cartItems: [],
      cartCount: 0,
      cartValue: 0,
      items: []
    };
  }

  fetchUser = async () => {
    const user = await axios.get('/api/current_user');
    console.log(user);
    this.setState({"user": user});
  }
  
  setItems = (items) => {
    this.setState({items: items});
  }

  addToCart = (name, price) => {
    this.setState({ cart: { 
      items: [this.state.cartItems, ...name],
      count: this.state.cartCount++,
      value: this.state.cartValue+price
    }});
  }

  render() {
    return (
      <BrowserRouter>
        <div>
          <Header items={this.state.items} count={this.state.cartCount}/>
          <Route 
            exact path="/" 
            render = {
              (props) => <Home {...props} 
              items={this.state.items} 
              setItems={this.setItems}
              addToCart={this.addToCart}
              />
            }
          />
          <Route 
            exact path="/shop" 
            render = {
              (props) => <Shop {...props} 
              items={this.state.items} 
              setItems={this.setItems}
              addToCart={this.addToCart}
              />
            }
          />
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
