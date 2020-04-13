import React from 'react';
import logo from './logo.svg';
import './App.css';
import {Component} from "react";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {error:null,
    items: []
    }
  }
 
 
  render() {
     return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <ul>
          {
              this.state.items.map(i =>
                <li key={i.id}>{i.name}</li>
              )
          }
        </ul>
      </header>
    </div>
  );
  }
  componentDidMount(){
    fetch("http://localhost:8888/servletfunc")
.then()

    .then((response) => response.json())
    .then((response) => {
            this.setState({items: response});
            this.setState({isLoaded: true});
    })
    .then((error) => {
        this.setState({false: true});
        this.setState({error});
    })

  }


}


export default App;
