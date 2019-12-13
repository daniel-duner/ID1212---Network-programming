import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import axios from 'axios';


class Home extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      user: ""
    }
  }
  login = async () => {
      const user = await axios.get('/auth/google')
      return user;
  }
  render() {
    return (
        <div>
            <div>Welcome to the shop!</div>
            <Button variant="primary" onClick={this.login}>Login with Google</Button>
            <div>{this.state.user}</div>
        </div>
    );
  }
}

export default Home;
