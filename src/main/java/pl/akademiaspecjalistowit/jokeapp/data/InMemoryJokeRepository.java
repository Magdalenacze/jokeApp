package pl.akademiaspecjalistowit.jokeapp.data;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryJokeRepository implements JokeRepository {

   private final List<Joke> jokes;

    public InMemoryJokeRepository() {
        this.jokes = List.of(
                new Joke("What is an object-oriented way to make a fortune? Inheritance",
                        "Programmers"),
                new Joke("What does an IT specialist say when he receives a pendrive for " +
                        "his birthday? Thanks for remembering",
                        "Programmers"),
                new Joke("What is the difference between programmers and politicians? " +
                        "Programmers are only paid for programs that work",
                        "Politics"),
                new Joke("How can you tell if a politician is lying? By the way he moves his mouth...",
                        "Politics"),
                new Joke("How on earth did you manage to become a professional bodybuilder " +
                        "when you were always a loser in Physical Education? Because I was always a top student in chemistry",
                        "Sport"),
                new Joke("Why does a Polish national team fan need a scarf? " +
                        "So that he would have something to wipe his tears with",
                        "Sport"));
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
