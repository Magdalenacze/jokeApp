package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;
import pl.akademiaspecjalistowit.jokeapp.data.SourceType;

import java.util.List;

public class JokeServiceImpl implements JokeService {

    private JokeProvider jokeProvider;

    public JokeServiceImpl(SourceType sourceType) {
        switch (sourceType) {
            case IN_MEMORY:
                this.jokeProvider = new JokeDataProvider(SourceType.IN_MEMORY);
                break;
            case API:
                this.jokeProvider = new JokeApiProvider();
                break;
            case FILE:
                this.jokeProvider = new JokeDataProvider(SourceType.FILE);
                break;
            default:
                this.jokeProvider = new JokeDataProvider(SourceType.IN_MEMORY);
                break;
        }
    }

    @Override
    public Joke getJoke() {
        return jokeProvider.getJoke();
    }

    @Override
    public Joke getJoke(String category) {
        return jokeProvider.getJokeByCategory(category);
    }

    public List<String> showAvailableCategories() {
        return jokeProvider.showAvailableCategories();
    }
}
