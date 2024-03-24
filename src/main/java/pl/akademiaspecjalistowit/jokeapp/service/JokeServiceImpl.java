package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.util.List;

public class JokeServiceImpl implements JokeService {

    private JokeProvider jokeProvider;

    public JokeServiceImpl(int choiceFromTheSourceList) {
        switch (choiceFromTheSourceList) {
            case 1:
                this.jokeProvider = new JokeDataProvider();
                break;
            case 2:
                this.jokeProvider = new JokeApiProvider();
                break;
            default:
                this.jokeProvider = new JokeDataProvider();
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
