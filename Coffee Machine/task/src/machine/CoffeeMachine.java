package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private State currentState = State.EXPECTING_ACTION;
    private int water = 400;
    private int milk = 540;
    private int coffeeBeans = 120;
    private int disposableCups = 9;
    private int money = 550;

    public State getCurrentState() {
        return currentState;
    }

    enum Action {
        BUY("buy"), FILL("fill"), TAKE("take"), REMAINING("remaining"), EXIT("exit");

        final String action;

        Action(String action) {
            this.action = action;
        }

    }

    enum Coffee {
        ESPRESSO(250, 0, 16, 4, "1"),
        LATTE(350, 75, 20, 7, "2"),
        CAPPUCCINO(200, 100, 12, 6, "3");

        final int water;
        final int milk;
        final int coffeeBeans;
        final int money;
        final String option;

        Coffee(int water, int milk, int coffeeBeans, int money, String option) {
            this.water = water;
            this.milk = milk;
            this.coffeeBeans = coffeeBeans;
            this.money = money;
            this.option = option;
        }

    }

    enum State {
        EXPECTING_ACTION, EXPECTING_ORDER, EXPECTING_WATER, EXPECTING_MILK, EXPECTING_BEANS, EXPECTING_CUPS, FINISH

    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final CoffeeMachine coffeeMachine = new CoffeeMachine();

        System.out.println("Write action (buy, fill, take, remaining, exit):");

        while (coffeeMachine.getCurrentState() != State.FINISH) {
            coffeeMachine.call(scanner.next());

            switch (coffeeMachine.getCurrentState()) {
                case EXPECTING_ORDER: {
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    break;
                }
                case EXPECTING_WATER: {
                    System.out.println("Write how many ml of water do you want to add:");
                    break;
                }
                case EXPECTING_MILK: {
                    System.out.println("Write how many ml of milk do you want to add:");
                    break;
                }
                case EXPECTING_BEANS: {
                    System.out.println("Write how many grams of coffee beans do you want to add:");
                    break;
                }
                case EXPECTING_CUPS: {
                    System.out.println("Write how many disposable cups of coffee do you want to add:");
                    break;
                }
                case EXPECTING_ACTION: {
                    System.out.println("Write action (buy, fill, take, remaining, exit):");
                    break;
                }
            }
        }
    }

    public void call(String input) {
        switch (currentState) {
            case EXPECTING_ACTION: {
                performAction(input);
                break;
            }
            case EXPECTING_ORDER: {
                buy(input);
                break;
            }
            case EXPECTING_WATER:
            case EXPECTING_MILK:
            case EXPECTING_BEANS:
            case EXPECTING_CUPS: {
                fill(currentState, input);
                break;
            }
        }
    }

    private void performAction(String input) {
        final Action action = Action.valueOf(input.toUpperCase());
        switch (action) {
            case BUY: {
                currentState = State.EXPECTING_ORDER;
                break;
            }
            case FILL: {
                currentState = State.EXPECTING_WATER;
                break;
            }
            case TAKE: {
                takeMoney();
                currentState = State.EXPECTING_ACTION;
                break;
            }
            case REMAINING: {
                printRemaining();
                currentState = State.EXPECTING_ACTION;
                break;
            }
            case EXIT: {
                currentState = State.FINISH;
                break;
            }
        }
    }

    private void buy(String input) {
        switch (input) {
            case "1": {
                if (checkIngredients(Coffee.ESPRESSO)) {
                    water -= Coffee.ESPRESSO.water;
                    coffeeBeans -= Coffee.ESPRESSO.coffeeBeans;
                    disposableCups--;
                    money += Coffee.ESPRESSO.money;
                } else {
                    printMessage(Coffee.ESPRESSO);
                }
                break;
            }
            case "2": {
                if (checkIngredients(Coffee.LATTE)) {
                    water -= Coffee.LATTE.water;
                    milk -= Coffee.LATTE.milk;
                    coffeeBeans -= Coffee.LATTE.coffeeBeans;
                    disposableCups--;
                    money += Coffee.LATTE.money;
                } else {
                    printMessage(Coffee.LATTE);
                }
                break;
            }
            case "3": {
                if (checkIngredients(Coffee.CAPPUCCINO)) {
                    water -= Coffee.CAPPUCCINO.water;
                    milk -= Coffee.CAPPUCCINO.milk;
                    coffeeBeans -= Coffee.CAPPUCCINO.coffeeBeans;
                    disposableCups--;
                    money += Coffee.CAPPUCCINO.money;
                } else {
                    printMessage(Coffee.CAPPUCCINO);
                }
                break;
            }
            case "back": {
                break;
            }
        }
        currentState = State.EXPECTING_ACTION;
    }

    private void fill(State currentState, String input) {
        final int amount = Integer.parseInt(input);
        switch (currentState) {
            case EXPECTING_WATER: {
                water += amount;
                this.currentState = State.EXPECTING_MILK;
                break;
            }
            case EXPECTING_MILK: {
                milk += amount;
                this.currentState = State.EXPECTING_BEANS;
                break;
            }
            case EXPECTING_BEANS: {
                coffeeBeans += amount;
                this.currentState = State.EXPECTING_CUPS;
                break;

            }
            case EXPECTING_CUPS: {
                disposableCups += amount;
                this.currentState = State.EXPECTING_ACTION;
                break;
            }
        }
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d\n", money);
        money = 0;
    }

    private void printRemaining() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", water);
        System.out.printf("%d of milk\n", milk);
        System.out.printf("%d of coffee beans\n", coffeeBeans);
        System.out.printf("%d of disposable cups\n", disposableCups);
        if (money > 0) System.out.printf("$%d of money\n", money);
        else System.out.printf("%d of money\n", money);
    }

    private boolean checkIngredients(Coffee coffee) {

        return this.water >= coffee.water && this.milk >= coffee.milk && this.coffeeBeans >= coffee.coffeeBeans && this.disposableCups >= 1;
    }

    private void printMessage(Coffee coffee) {
        if (this.water < coffee.water) System.out.println("Sorry, not enough water!");
        if (this.milk < coffee.milk) System.out.println("Sorry, not enough milk!");
        if (this.coffeeBeans < coffee.coffeeBeans) System.out.println("Sorry, not enough coffee beans!");
        if (this.disposableCups < 1) System.out.println("Sorry, not enough disposable cups!");
    }
}
