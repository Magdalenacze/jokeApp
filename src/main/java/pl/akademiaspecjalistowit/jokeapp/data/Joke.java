package pl.akademiaspecjalistowit.jokeapp.data;

import java.io.Serializable;
import java.util.UUID;

public class Joke implements Serializable {

    private UUID id;
    private String content;
    private String category;

    public Joke(String content, String category) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return content;
    }
}