package pl.akademiaspecjalistowit.jokeapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.akademiaspecjalistowit.jokeapp.data.Joke;
import pl.akademiaspecjalistowit.jokeapp.data.JokeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JokeDataProviderTest {

    @Test
    void should_throw_exception_when_no_repositories_are_available() {
        // given
        List<JokeRepository> emptyRepositoriesList = List.of();

        // when
        Executable e = () -> new JokeDataProvider(emptyRepositoriesList);

        // then
        assertThrows(RuntimeException.class, e);
    }

    @Test
    void should_call_get_joke_from_repository_on_get_joke_call_on_provider() {
        // given
        Joke joke = new Joke("a1", "a2");
        List<JokeRepository> jokeRepositories = List.of(new JokeRepository() {

            @Override
            public List<Joke> getAllJokes() {
                return List.of(joke);
            }

            @Override
            public List<Joke> getAllByCategory(String category) {
                return null;
            }
        });
        JokeDataProvider jokeDataProvider = new JokeDataProvider(jokeRepositories);

        //when
        Joke jokeFromProvider = jokeDataProvider.getJoke();

        //then
        assertEquals(joke, jokeFromProvider);
    }

    @Test
    void should_call_get_joke_by_category_from_repository_on_get_joke_by_category_call_on_provider() {
        //given
        Joke joke = new Joke("a1", "a2");

        List<JokeRepository> jokeRepositories = provideSingleRepositoryReturningJokeByCategory(joke);
        JokeDataProvider jokeDataProvider = new JokeDataProvider(jokeRepositories);

        //when
        Joke jokeFromProvider = jokeDataProvider.getJokeByCategory("whatever");

        //then
        assertEquals(joke, jokeFromProvider);
    }

    @Test
    void should_alter_with_round_robin_between_providers() {
        //given
        Joke jokeA = new Joke("a1", "a2");
        Joke jokeB = new Joke("b1", "b2");

        List<JokeRepository> jokeRepositories = provideSingleRepositoryReturningJokeByCategory(jokeA);
        jokeRepositories.addAll(provideSingleRepositoryReturningJokeByCategory(jokeB));
        JokeDataProvider jokeDataProvider = new JokeDataProvider(jokeRepositories);

        //when
        List<Joke> jokes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            jokes.add(jokeDataProvider.getJoke());
        }

        //then
        assertThatJokesAreAltering(jokeA, jokeB, jokes);
    }

    private void assertThatJokesAreAltering(Joke jokeA, Joke jokeB, List<Joke> jokes) {
        assertEquals(jokes.get(0), jokeA);
        assertEquals(jokes.get(1), jokeB);
        assertEquals(jokes.get(2), jokeA);
        assertEquals(jokes.get(3), jokeB);
    }

    private List<JokeRepository> provideSingleRepositoryReturningJokeByCategory(Joke joke) {
        List<JokeRepository> jokeRepositories = new ArrayList<>();

        jokeRepositories.add(new JokeRepository() {

            @Override
            public List<Joke> getAllJokes() {
                return List.of(joke);
            }

            @Override
            public List<Joke> getAllByCategory(String category) {
                return List.of(joke);
            }
        });
        return jokeRepositories;
    }
}