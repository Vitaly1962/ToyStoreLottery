package com.toystore;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ToyStoreConsoleUI {
    private ToyStore store;
    private Lottery lottery;
    private Scanner scanner;

    public ToyStoreConsoleUI(ToyStore store) {
        this.store = store;
        this.lottery = new Lottery();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить новую игрушку");
            System.out.println("2. Запустить розыгрыш");
            System.out.println("3. Показать список игрушек");
            System.out.println("4. Выйти из программы");
            System.out.print("Введите номер действия: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addToy();
                    break;
                case 2:
                    runLottery();
                    break;
                case 3:
                    store.listToys();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте еще раз.");
            }

            // Добавляем пустую строку для разделения сессий
            System.out.println();
        }
    }

    private void addToy() {
        System.out.print("Введите информацию о новой игрушке в формате 'id название частота': ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            try {
                String id = parts[0];
                String name = parts[1];
                int frequency = Integer.parseInt(parts[2]);
                store.addToy(new Toy(id, name, frequency));
                store.saveToysToFile("toys.txt"); // Сохранение игрушек сразу после добавления
                System.out.println("Игрушка успешно добавлена.");
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат частоты. Попробуйте еще раз.");
            }
        } else {
            System.out.println("Некорректный формат данных. Попробуйте еще раз.");
        }
    }

    private void runLottery() {
        System.out.print("Введите количество итераций для розыгрыша: ");
        int iterations = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String[] winners = new String[iterations];
        Map<String, Integer> toyCount = new HashMap<>();

        for (int i = 0; i < iterations; i++) {
            String toy = store.getToy();
            winners[i] = toy;
            toyCount.put(toy, toyCount.getOrDefault(toy, 0) + 1);
        }

        System.out.println("Результаты розыгрыша:");
        for (Map.Entry<String, Integer> entry : toyCount.entrySet()) {
            System.out.println(entry.getKey() + " выпала " + entry.getValue() + " раз");
        }

        lottery.saveWinnersToFile(winners, "winners.txt");
        System.out.println("Данные успешно записаны в файл: winners.txt");
    }
}
