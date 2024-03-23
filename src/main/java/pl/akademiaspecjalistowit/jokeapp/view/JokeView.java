package pl.akademiaspecjalistowit.jokeapp.view;

import pl.akademiaspecjalistowit.jokeapp.service.JokeServiceImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class JokeView {

    private Scanner scanner;
    private JokeServiceImpl jokeServiceImpl;

    public JokeView() {
        this.scanner = new Scanner(System.in);
        this.jokeServiceImpl = new JokeServiceImpl();
    }

    public void run() {
        int choiceFromTheUserMenu = -1;
        int choiceFromTheCategoryList = -1;

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
                    wantARandomJokeFromAnyCategory();
                    break;
                case 2:
                    showCategoryList();
                    try {
                        choiceFromTheCategoryList = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException ex) {
                        scanner.nextLine();
                    }
                    wantARandomJokeFromSpecificCategory
                            (jokeServiceImpl.showAvailableCategories().get(choiceFromTheCategoryList - 1));
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

    private void showUserMenu() {
        System.out.println("User, welcome to JokeApp and have fun!\n" +
                "You can choose from the following options:\n" +
                "If you want to draw a joke from any category - enter 1:\n" +
                "If you want to randomly select a joke from a specific category - enter 2:\n" +
                "Logout - enter 0:");
    }

    private void wantARandomJokeFromAnyCategory() {
        System.out.println("I'm displaying a joke for a random category...");
        System.out.println(jokeServiceImpl.getJoke());
        System.out.println();
    }

    private void showCategoryList() {
        List<String> categories = jokeServiceImpl.showAvailableCategories();

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        System.out.println("Enter the category number: ");
    }

    private void wantARandomJokeFromSpecificCategory(String category) {
        System.out.println("I'm displaying a random joke from the category " + category + "...");
        System.out.println(jokeServiceImpl.getJoke(category));
        System.out.println();
    }

    private void exitingTheApplication( ) {
        System.out.println("Thank you! I hope you had fun!");
    }
}
