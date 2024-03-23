package pl.akademiaspecjalistowit.jokeapp.data;

import java.util.List;

public interface JokeRepository {

    List<Joke> getAllJokes();

    List<Joke> getAllByCategory(String category);
}
