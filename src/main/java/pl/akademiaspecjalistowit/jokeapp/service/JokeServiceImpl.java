package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.InMemoryJokeRepository;
import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JokeServiceImpl implements JokeService {

    private InMemoryJokeRepository inMemoryJokeRepository;

    public JokeServiceImpl() {
        this.inMemoryJokeRepository = new InMemoryJokeRepository();
    }

    @Override
    public Joke getJoke() {
        Random rand = new Random();
        List<Joke> anyJokes = inMemoryJokeRepository.getAllJokes();

        return anyJokes.get(rand.nextInt(anyJokes.size()));
    }

    @Override
    public Joke getJoke(String category) {
        Random rand = new Random();
        List<Joke> jokesByCategory = inMemoryJokeRepository.getAllByCategory(category);

        return jokesByCategory.get(rand.nextInt(jokesByCategory.size()));
    }

    public List<String> showAvailableCategories() {
        return inMemoryJokeRepository.getAllJokes()
                .stream()
                .map(Joke::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}
