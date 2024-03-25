package pl.akademiaspecjalistowit.jokeapp.data;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileJokeRepository implements JokeRepository {

    private ObjectInputStream jokeInput;
    private List<Joke> jokes;

    public FileJokeRepository() {
        try {
            jokeInput = new ObjectInputStream(new FileInputStream("FileJokeRepository"));
            jokes = (List<Joke>) jokeInput.readObject();
            jokeInput.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokes;
    }

    @Override
    public List<Joke> getAllByCategory(String category) {
        return jokes
                .stream()
                .filter(compareCategories(category))
                .collect(Collectors.toList());
    }

    private static Predicate<Joke> compareCategories(String category) {
        return c -> c.getCategory().equals(category);
    }
}
