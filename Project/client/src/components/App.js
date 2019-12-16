import React from 'react';
import Shop from './Shop';
import Header from './Header';
import Home from './Home';
import Cart from './Cart';
import { BrowserRouter, Route } from "react-router-dom";
import {connect} from 'react-redux';
import * as actions from '../actions';


class App extends React.Component {
  componentDidMount(){
    this.props.fetchUser()
    this.props.fetchCart();
  }

  render() {
    return (
      <BrowserRouter>
        <div>
          <Header />
          <Route exact path="/" component={Shop} />
          <Route exact path="/shop" component={Shop} />
          <Route exact path="/cart" component={Cart} />
        </div>
      </BrowserRouter>
    );
  }
}

export default connect(null, actions)(App);
