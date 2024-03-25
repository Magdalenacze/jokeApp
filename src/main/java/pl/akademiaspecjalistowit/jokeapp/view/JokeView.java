package pl.akademiaspecjalistowit.jokeapp.view;

import pl.akademiaspecjalistowit.jokeapp.data.SourceType;
import pl.akademiaspecjalistowit.jokeapp.service.JokeServiceImpl;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class JokeView {

    private Scanner scanner;
    List<String> sources;

    public JokeView() {
        this.scanner = new Scanner(System.in);
        this.sources = List.of(
                "Pool of jokes from the JokeApp application",
                "Pool of jokes from the Internet",
                "Pool of jokes from additional file");
    }

    public void run() {
        int choiceFromTheUserMenu = -1;
        int choiceFromTheCategoryList = -1;
        int choiceFromTheSourceList = -1;

        showSourceList();
        try {
            choiceFromTheSourceList = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException ex) {
            scanner.nextLine();
        }

        JokeServiceImpl jokeServiceImpl = getSourceTypeByUserChoice(choiceFromTheSourceList);

        do {
            showUserMenu();
            try {
                choiceFromTheUserMenu = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException ex) {
                scanner.nextLine();
            }
            switch (choiceFromTheUserMenu) {
                case 1:
                    wantARandomJokeFromAnyCategory(jokeServiceImpl);
                    break;
                case 2:
                    showCategoryList(jokeServiceImpl);
                    try {
                        choiceFromTheCategoryList = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException ex) {
                        scanner.nextLine();
                    }
                    wantARandomJokeFromSpecificCategory(jokeServiceImpl.showAvailableCategories()
                            .get(choiceFromTheCategoryList - 1), jokeServiceImpl);
                    break;
                case 0:
                    exitingTheApplication();
                    break;
                default:
                    System.out.println("Incorrect value entered!");
                    System.out.println();
            }
        } while (choiceFromTheUserMenu != 0);
        scanner.close();
    }

    private static JokeServiceImpl getSourceTypeByUserChoice(int choiceFromTheSourceList) {
        final int choice = choiceFromTheSourceList;

        JokeServiceImpl jokeServiceImpl = new JokeServiceImpl(Arrays
                .stream(SourceType.values())
                .filter(p -> p.getChoiceFromTheSourceList() == choice)
                .findFirst()
                .get());
        return jokeServiceImpl;
    }

    private void showSourceList() {
        System.out.println("User, welcome to JokeApp and have fun!\n" +
                "You can choose from the following options:");
        for (int i = 0; i < sources.size(); i++) {
            System.out.println((i + 1) + ". " + sources.get(i));
        }
        System.out.println("Enter the source number of the joke pool you want to use: ");
    }

    private void showUserMenu() {
        System.out.println("Select further options:\n" +
                "If you want to draw a joke from any category - enter 1:\n" +
                "If you want to randomly select a joke from a specific category - enter 2:\n" +
                "Logout - enter 0:");
    }

    private void wantARandomJokeFromAnyCategory(JokeServiceImpl jokeServiceImpl) {
        System.out.println("I'm displaying a joke for a random category...");
        System.out.println(jokeServiceImpl.getJoke());
        System.out.println();
    }

    private void showCategoryList(JokeServiceImpl jokeServiceImpl) {
        List<String> categories = jokeServiceImpl.showAvailableCategories();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        System.out.println("Enter the category number: ");
    }

    private void wantARandomJokeFromSpecificCategory(String category, JokeServiceImpl jokeServiceImpl) {
        System.out.println("I'm displaying a random joke from the category " + category + "...");
        System.out.println(jokeServiceImpl.getJoke(category));
        System.out.println();
    }

    private void exitingTheApplication( ) {
        System.out.println("Thank you! I hope you had fun!");
    }
}
