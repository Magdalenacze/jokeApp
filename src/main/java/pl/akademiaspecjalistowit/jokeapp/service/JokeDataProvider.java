package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JokeDataProvider implements JokeProvider {

    private JokeRepository jokeRepository;

    public JokeDataProvider(SourceType sourceType) {
        switch (sourceType) {
            case IN_MEMORY:
                jokeRepository = new InMemoryJokeRepository();
                break;
            case FILE:
                jokeRepository = new FileJokeRepository();
                break;
            default:
                jokeRepository = new InMemoryJokeRepository();
        }
    }

    @Override
    public Joke getJoke() {
        Random rand = new Random();
        List<Joke> anyJokes = jokeRepository.getAllJokes();
        return anyJokes.get(rand.nextInt(anyJokes.size()));
    }

    @Override
    public Joke getJokeByCategory(String category) {
        Random rand = new Random();
        List<Joke> jokesByCategory = jokeRepository.getAllByCategory(category);
        return jokesByCategory.get(rand.nextInt(jokesByCategory.size()));
    }

    public List<String> showAvailableCategories() {
        return jokeRepository.getAllJokes()
                .stream()
                .map(Joke::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}