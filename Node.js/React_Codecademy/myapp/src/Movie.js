import React, { Component } from 'react';
import propTypes from 'prop-types';
import './App.css';

function Movie({title, poster, genres, synopsis}){
    return (
        <div className="Movie">
            <div className="Movie__Columns">
                <MoviePoster poster={poster} alt={title}/>
            </div>
            <div className="Movie__Columns">
                <h1>{title}</h1>
                <div className="Movie__Genres">
                    {genres.map((genre, index) => <MovieGenres genre={genre} key={index} />)}
                </div>
                <p className="Movie__Synopsis">
                    {synopsis}
                </p>
            </div>
        </div>
    );
}

Movie.propTypes = {
  title: propTypes.string.isRequired,
  poster: propTypes.string.isRequired,
  genres: propTypes.array.isRequired,
  synopsis: propTypes.string.isRequired
}


function MoviePoster({poster, alt}){
  return(
    <img className="Movie__Poster" alt={alt} src={poster}/>
  )
}

MoviePoster.propTypes = {
  poster: propTypes.string.isRequired,
  alt: propTypes.string.isRequired
}
export default Movie;

function MovieGenres({genre}){
    return(
        <span className="Movie__Genre">{genre}</span>
    )
}

MovieGenres.propTypes = {
    genre: propTypes.string.isRequired
}