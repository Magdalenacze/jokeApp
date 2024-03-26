package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.util.List;

public class JokeServiceImpl implements JokeService {

    private final List<JokeProvider> jokeProviders;
    private long counter = 0;

    public JokeServiceImpl(List<JokeProvider> jokeProviders) {
        if (jokeProviders == null || jokeProviders.isEmpty()) {
            throw new RuntimeException("Required at least one JokeProvider for the application to run!");
        }
        this.jokeProviders = jokeProviders;
    }

    @Override
    public Joke getJoke() {
        return getJokeProvider().getJoke();
    }

    @Override
    public Joke getJoke(String category) {
       return getJokeProvider().getJokeByCategory(category);
    }

    @Override
    public List<String> getAvailableJokeCategories() {
        return getJokeProvider().getAvailableJokeCategories();
    }

    @Override
    public void incrementCounter(){
        counter++;
    }

    private JokeProvider getJokeProvider() {
        return jokeProviders.get((int) counter % jokeProviders.size());
    }
}
