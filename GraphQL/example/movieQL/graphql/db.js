import fetch from "node-fetch";

let movies = [
  {
    id: 0,
    name: "Star Wars - The new one",
    score: 1
  },
  {
    id: 1,
    name: "Avengers - The new one",
    score: 8
  },
  {
    id: 2,
    name: "The Godfather I",
    score: 99
  },
  {
    id: 3,
    name: "Logan",
    score: 2
  }
];

export const getMovies = () => movies;

export const getById = (id) => {
  const filteredMovies = movies.filter(movie => id === movie.id);
  // people 배열의 모든 값들을 체크해 두 id 값이 같은 movie를 movie 대입해 반환한 뒤,
  // 이를 movies의 filter 함수의 인자로 준다.
  // filter : 모두 돌다가 해당 condition에 맞는 값 하나를 return

  return filteredMovies[0];
}

export const deleteMovie = (id) => {
  const cleanedMovies = movies.filter(movie => id !== movie.id);

  if (movies.length > cleanedMovies.length) {
    movies = cleanedMovies;
    return true;
  } else {
    return false;
  }
}

export const addMovie = (name, score) => {
  const newMovie = {
    id: `${movies.length + 1}`,
    name,
    score
  }
  movies.push(newMovie);

  return newMovie;
}

/* RESTful API 감싸기 */
const API_URL = "https://yts.am/api/v2/list_movies.json";

export const getMoviesByApi = (limit, rating) => {
  let REQUEST_URL = API_URL;

  if (limit > 0) {
    REQUEST_URL += `?limit=${limit}`;
  }

  if (rating > 0) {
    REQUEST_URL += `&minimum_rating=${rating}`;
  }

  return fetch(`${REQUEST_URL}`)
    .then(res => res.json())
    .then(json => json.data.movies);
}
