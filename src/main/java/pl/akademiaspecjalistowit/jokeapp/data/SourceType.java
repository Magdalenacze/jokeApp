package pl.akademiaspecjalistowit.jokeapp.data;

public enum SourceType {
    IN_MEMORY (1),
    API (2),
    FILE (3);

    private int choiceFromTheSourceList;

    public int getChoiceFromTheSourceList() {
        return choiceFromTheSourceList;
    }

    SourceType (int choiceFromTheSourceList) {
        this.choiceFromTheSourceList = choiceFromTheSourceList;
    }
}
