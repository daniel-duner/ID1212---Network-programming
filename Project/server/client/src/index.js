import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import Home from './components/Home'


class App extends React.Component {
    render() {
      return(
          <Home/>
      );
    }
  }

ReactDOM.render(<App />, document.getElementById('root'))
