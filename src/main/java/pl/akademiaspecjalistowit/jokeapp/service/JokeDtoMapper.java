package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;

public interface JokeDtoMapper {

    public static Joke toJoke(JokeDto jokeDto) {
        return new Joke(jokeDto.getSetup() + "\n" + jokeDto.getDelivery(),
                jokeDto.getCategory());
    }
}
