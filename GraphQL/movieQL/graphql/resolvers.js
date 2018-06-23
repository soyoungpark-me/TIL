import { getMovies, getById, addMovie, deleteMovie, getMoviesByApi } from "./db";

const resolvers = {
  Query: {
    movies: () => getMovies(),
    moviesByApi: (_, {limit, rating}) => getMoviesByApi(limit, rating),
    // person: () => getById() // argument를 줘야 한다!
    movie: (_, { id }) => getById(id)
  },
  Mutation: {
    addMovie: (_, { name, score }) => addMovie(name, score),
    deleteMovie: (_, { id }) => deleteMovie(id)
  }
};

export default resolvers;