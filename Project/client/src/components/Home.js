import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import {connect} from 'react-redux';

class Home extends React.Component {

  render() {
    return (
        <div>
            <div>Welcome to the shop!</div>
           
        </div>
    );
  }
}

const mapStateToProps = ({auth}) => {
  return{auth};
}

export default connect(mapStateToProps)(Home);