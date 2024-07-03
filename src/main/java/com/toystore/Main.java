package com.toystore;

public class Main {
    public static void main(String[] args) {
        ToyStore store = new ToyStore();

        // Загрузка игрушек из файла
        store.loadToysFromFile("toys.txt");

        // Запуск интерфейса пользователя
        ToyStoreConsoleUI ui = new ToyStoreConsoleUI(store);
        ui.start();

        // Сохранение игрушек в файл
        store.saveToysToFile("toys.txt");
    }
}