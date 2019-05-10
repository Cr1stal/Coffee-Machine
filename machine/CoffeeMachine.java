package machine;

import java.util.Scanner;

enum State {
    ASK_ABOUT_PROGRAM,
    CHOOSE_PROGRAM,
    BUY,
    CHOOSE_TYPE_COFFEE,
    ASK_ABOUT_FILL_WATER,
    ASK_ABOUT_FILL_MILK,
    ASK_ABOUT_FILL_COFFEE_BEANS,
    ASK_ABOUT_FILL_DISPOSABLE_CUPS,
    FILL,
    FILL_WATER,
    FILL_MILK,
    FILL_COFFEE_BEANS,
    FILL_DISPOSABLE_CUPS,
    REMAINING,
    TAKE,
    EXIT
};

public class CoffeeMachine {
    private static int currentAmountOfWater = 400;
    private static int currentAmountOfMilk = 540;
    private static int currentAmountOfCoffeeBeans = 120;
    private static int currentAmountOfDisposableCups = 9;
    private static int currentAmountOfMoney = 550;
    private static Scanner in = new Scanner(System.in);
    private static State state = State.ASK_ABOUT_PROGRAM;

    public static void main(String[] args) {

        executeAction("");

        while (true) {
            if (in.hasNext()) {
                String action = in.next();

                executeAction(action);
                if (state == State.EXIT) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    private static void executeAction(String action) {
        switch (state) {
            case ASK_ABOUT_PROGRAM:
                System.out.print("Write action (buy, fill, take, remaining, exit): ");
                state = State.CHOOSE_PROGRAM;
                break;
            case CHOOSE_PROGRAM:
                if (action.equals("buy")) {
                    state = State.BUY;
                    executeAction(action);
                } else if (action.equals("fill")) {
                    state = State.FILL;
                    executeAction(action);
                } else if (action.equals("take")) {
                    state = State.TAKE;
                    executeAction(action);
                } else if (action.equals("remaining")) {
                    state = State.REMAINING;
                    executeAction(action);
                } else if (action.equals("exit")) {
                    state = State.EXIT;
                    executeAction(action);
                } else {
                    state = State.ASK_ABOUT_PROGRAM;
                    executeAction(action);
                }
                break;
            case CHOOSE_TYPE_COFFEE:
                executeChooseTypeOfCoffeeAction(action);
                state = State.ASK_ABOUT_PROGRAM;
                executeAction(action);
                break;
            case FILL:
                executeFill();
                state = State.ASK_ABOUT_FILL_WATER;
                executeAction(action);
                break;
            case ASK_ABOUT_FILL_WATER:
                askAboutFillWater();
                state = State.FILL_WATER;
                break;
            case FILL_WATER:
                executeFillWater(action);
                state = State.ASK_ABOUT_FILL_MILK;
                executeAction(action);
                break;
            case ASK_ABOUT_FILL_MILK:
                askAboutFillMilk();
                state = State.FILL_MILK;
                break;
            case FILL_MILK:
                executeFillMilk(action);
                state = State.ASK_ABOUT_FILL_COFFEE_BEANS;
                executeAction(action);
                break;
            case ASK_ABOUT_FILL_COFFEE_BEANS:
                askAboutFillCoffeeBeans();
                state = State.FILL_COFFEE_BEANS;
                break;
            case FILL_COFFEE_BEANS:
                executeFillCoffeeBeans(action);
                state = State.ASK_ABOUT_FILL_DISPOSABLE_CUPS;
                executeAction(action);
                break;
            case ASK_ABOUT_FILL_DISPOSABLE_CUPS:
                askAboutFillDisposableCups();
                state = State.FILL_DISPOSABLE_CUPS;
                break;
            case FILL_DISPOSABLE_CUPS:
                executeFillDisposableCups(action);
                state = State.ASK_ABOUT_PROGRAM;
                executeAction(action);
                break;
            case REMAINING:
                executeRemainingAction();
                state = State.ASK_ABOUT_PROGRAM;
                executeAction(action);
                break;
            case TAKE:
                executeTakeAction();
                state = State.ASK_ABOUT_PROGRAM;
                executeAction(action);
                break;
            case BUY:
                askAboutTypeOfCoffee();
                state = State.CHOOSE_TYPE_COFFEE;
                break;
            case EXIT:
                break;
            default:
                state = State.ASK_ABOUT_PROGRAM;
                executeAction(action);
                break;
        }
    }

    private static void executeFill() {
        System.out.println("");
    }

    private static void executeChooseTypeOfCoffeeAction(String userChoiceOfCoffee) {
        if (userChoiceOfCoffee.equals("back")) {
            System.out.println("");
            return;
        }

        int typeOfCoffee = Integer.parseInt(userChoiceOfCoffee, 10);

        int neededAmountOfWater = 0;
        int neededAmountOfCoffeeBeans = 0;
        int neededAmountOfMilk = 0;
        int neededAmountOfDisposableCups = 1;
        int priceOfCoffee = 0;

        if (typeOfCoffee == 1) {
            neededAmountOfWater = 250;
            neededAmountOfCoffeeBeans = 16;
            priceOfCoffee = 4;
        } else if (typeOfCoffee == 2) {
            neededAmountOfWater = 350;
            neededAmountOfMilk = 75;
            neededAmountOfCoffeeBeans = 20;
            priceOfCoffee = 7;
        } else if (typeOfCoffee == 3) {
            neededAmountOfWater = 200;
            neededAmountOfMilk = 100;
            neededAmountOfCoffeeBeans = 12;
            priceOfCoffee = 6;
        } else {
            System.out.println("");
            return;
        }

        if (isEnough(currentAmountOfWater, neededAmountOfWater)) {
            System.out.println("Sorry, not enough water!");
            System.out.println("");
            return;
        }
        if (isEnough(currentAmountOfMilk, neededAmountOfMilk)) {
            System.out.println("Sorry, not enough milk!");
            System.out.println("");
            return;
        }
        if (isEnough(currentAmountOfCoffeeBeans, neededAmountOfCoffeeBeans)) {
            System.out.println("Sorry, not enough coffee beans!");
            System.out.println("");
            return;
        }
        if (isEnough(currentAmountOfDisposableCups, neededAmountOfDisposableCups)) {
            System.out.println("Sorry, not enough disposable cups!");
            System.out.println("");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");
        System.out.println("");

        currentAmountOfWater -= neededAmountOfWater;
        currentAmountOfMilk -= neededAmountOfMilk;
        currentAmountOfCoffeeBeans -= neededAmountOfCoffeeBeans;
        currentAmountOfDisposableCups -= neededAmountOfDisposableCups;
        currentAmountOfMoney += priceOfCoffee;
    }

    private static boolean isEnough(int currentAmount, int targetAmount) {
        return (currentAmount - targetAmount) < 0;
    }

    private static void askAboutTypeOfCoffee() {
        System.out.println("");
        System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
    }

    private static void askAboutFillWater() {
        System.out.print("Write how many ml of water do you want to add: ");
    }

    private static void askAboutFillMilk() {
        System.out.print("Write how many ml of milk do you want to add: ");
    }

    private static void askAboutFillCoffeeBeans() {
        System.out.print("Write how many grams of coffee beans do you want to add: ");
    }

    private static void askAboutFillDisposableCups() {
        System.out.print("Write how many disposable cups of coffee do you want to add: ");
    }

    private static void executeFillWater(String input) {
        currentAmountOfWater += Integer.parseInt(input, 10);
    }

    private static void executeFillMilk(String input) {
        currentAmountOfMilk += Integer.parseInt(input, 10);
    }

    private static void executeFillCoffeeBeans(String input) {
        currentAmountOfCoffeeBeans += Integer.parseInt(input, 10);
    }

    private static void executeFillDisposableCups(String input) {
        currentAmountOfDisposableCups += Integer.parseInt(input, 10);
        System.out.println("");
    }

    private static void executeTakeAction() {
        System.out.println("");
        System.out.printf("I gave you $%d\n", currentAmountOfMoney);
        System.out.println("");
        currentAmountOfMoney = 0;
    }

    private static void executeRemainingAction() {
        System.out.println("");
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", currentAmountOfWater);
        System.out.printf("%d of milk\n", currentAmountOfMilk);
        System.out.printf("%d of coffee beans\n", currentAmountOfCoffeeBeans);
        System.out.printf("%d of disposable cups\n", currentAmountOfDisposableCups);
        System.out.printf("%d of money\n", currentAmountOfMoney);
        System.out.println("");
    }
}
