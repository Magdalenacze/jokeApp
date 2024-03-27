package pl.akademiaspecjalistowit.jokeapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JokeServiceImplTest {

    @Test
    void should_throw_exception_when_no_providers_are_available() {
        // given
        List<JokeProvider> emptyProvidersList = List.of();

        // when
        Executable e = () -> new JokeServiceImpl(emptyProvidersList);

        // then
        assertThrows(RuntimeException.class, e);
    }

    @Test
    void should_call_get_joke_from_provider_on_get_joke_call_on_service() {
        // given
        Joke joke = new Joke("a1", "a2");

        List<JokeProvider> jokeProviders = List.of(new JokeProvider() {
            @Override
            public Joke getJoke() {
                return joke;
            }

            @Override
            public Joke getJokeByCategory(String category) {
                return null;
            }

            @Override
            public List<String> getAvailableJokeCategories() {
                return null;
            }
        });
        JokeServiceImpl jokeService = new JokeServiceImpl(jokeProviders);

        //when
        Joke jokeFromService = jokeService.getJoke();

        //then
        assertEquals(joke, jokeFromService);
    }

    @Test
    void should_call_get_joke_by_category_from_provider_on_get_joke_by_category_call_on_service() {
        //given
        Joke joke = new Joke("a1", "a2");

        List<JokeProvider> jokeProviders = provideSingleProviderReturningJokeByCategory(joke);
        JokeServiceImpl jokeService = new JokeServiceImpl(jokeProviders);

        //when
        Joke jokeFromService = jokeService.getJoke("whatever");

        //then
        assertEquals(joke, jokeFromService);
    }

    @Test
    void should_alter_with_round_robin_between_providers() {
        //given
        Joke jokeA = new Joke("a1", "a2");
        Joke jokeB = new Joke("b1", "b2");

        List<JokeProvider> jokeProviders = provideSingleProviderReturningJokeByCategory(jokeA);
        jokeProviders.addAll(provideSingleProviderReturningJokeByCategory(jokeB));
        JokeServiceImpl jokeService = new JokeServiceImpl(jokeProviders);

        //when
        List<Joke> jokes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            jokes.add(jokeService.getJoke("whatever"));
            jokeService.incrementCounter();
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

    private List<JokeProvider> provideSingleProviderReturningJokeByCategory(Joke joke) {
        List<JokeProvider> jokeProviders = new ArrayList<>();

        jokeProviders.add(new JokeProvider() {
            @Override
            public Joke getJoke() {
                return null;
            }

            @Override
            public Joke getJokeByCategory(String category) {
                return joke;
            }

            @Override
            public List<String> getAvailableJokeCategories() {
                return List.of("c1", "c2");
            }
        });
        return jokeProviders;
    }
}