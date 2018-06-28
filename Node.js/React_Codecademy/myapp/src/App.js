import React, { Component } from 'react';
import propTypes from 'prop-types';
import Movie from './Movie';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  state = {}

  componentDidMount(){
    this._getMovies();
  }

  _renderMoives = () => {
    const movies  = this.state.movies.map(movie => {
      return <Movie title={movie.title_english} poster={movie.medium_cover_image} 
        key={movie.id} genres={movie.genres} synopsis={movie.synopsis} />
    })

    return movies;
  }

  _getMovies = async () => {
    const movies = await this._callApi();
    this.setState({
      movies
    });
  }

  _callApi = () => {
    return fetch('https://yts.am/api/v2/list_movies.json?sort_by=rating')
    .then(response => response.json())
    .then(json => json.data.movies)
    .catch(err => console.log(err))
  }

  render() {
    return (
      <div className="App">
        {this.state.movies ? this._renderMoives() : "loading"}
      </div>
    );
  }
}

export default App;
