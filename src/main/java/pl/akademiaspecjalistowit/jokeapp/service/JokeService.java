package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;

public interface JokeService {

    Joke getJoke();

    Joke getJoke(String category);
}
