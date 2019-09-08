import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  componentDidMount() {
    window.onpopstate = function(event) {
      console.log(`location: ${document.location}, state: ${event.state}`)
    }
  }

  render() {
    return (
      <>
        <div className="App">
          <header className="App-header" style={{ height: '400px', padding: '50px' }}>
            <img src={logo} className="App-logo" alt="logo" />
            <p>
              Edit <code>src/App.tsx</code> and save to reload.
            </p>
            <a
              className="App-link"
              href="https://reactjs.org"
              target="_blank"
              rel="noopener noreferrer"
            >
              Learn React
            </a>
          </header>
        </div>
        <div className="work-space">
          <button onClick={() => { window.history.pushState('v1', '', '/page1')}}>
            page1
          </button>
          <button onClick={() => { window.history.pushState('v1', '', '/page2')}}>
            page2
          </button>
        </div>
      </>
    )
  }
}

export default App;
