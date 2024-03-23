package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JokeServiceImpl implements JokeService {

    List<Joke> jokes;

    public JokeServiceImpl() {
        this.jokes = List.of(
                new Joke("What is an object-oriented way to make a fortune? Inheritance",
                        "Programmers"));
    }

    @Override
    public Joke getJoke() {
        Random rand = new Random();

        return jokes.get(rand.nextInt(jokes.size()));
    }

    @Override
    public Joke getJoke(String category) {
        List<Joke> jokesByCategory = jokes
                .stream()
                .filter(compareCategories(category))
                .collect(Collectors.toList());

        Random rand = new Random();

        return jokesByCategory.get(rand.nextInt(jokesByCategory.size()));
    }

    private static Predicate<Joke> compareCategories(String category) {
        return c -> c.getCategory().equals(category);
    }

    public List<String> showAvailableCategories() {
        return jokes
                .stream()
                .map(Joke::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}
