package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JokeDataProvider implements JokeProvider {

    private final List<JokeRepository> jokeRepositories;
    private static long counter = 0;

    public JokeDataProvider(List<JokeRepository> jokeRepositories) {
        this.jokeRepositories = jokeRepositories;
    }

    @Override
    public Joke getJoke() {
        Random rand = new Random();
        List<Joke> anyJokes = getJokeRepository().getAllJokes();
        counter++;
        return anyJokes.get(selectJokeIndex(rand, anyJokes));
    }

    @Override
    public Joke getJokeByCategory(String category) {
        Random rand = new Random();
        List<Joke> jokesByCategory = getJokeRepository().getAllByCategory(category);
        counter++;
        return jokesByCategory.get(selectJokeIndex(rand, jokesByCategory));
    }

    @Override
    public List<String> getAvailableJokeCategories() {
        return getJokeRepository().getAllJokes()
                .stream()
                .map(Joke::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    private JokeRepository getJokeRepository() {
        return jokeRepositories.get((int) counter % jokeRepositories.size());
    }

    private static int selectJokeIndex(Random rand, List<Joke> anyJokes) {
        return rand.nextInt(anyJokes.size());
    }
}