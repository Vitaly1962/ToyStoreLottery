package com.toystore;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyStore {
    private PriorityQueue<Toy> toyQueue;

    public ToyStore() {
        toyQueue = new PriorityQueue<>((a, b) -> b.getFrequency() - a.getFrequency());
    }

    public void addToy(Toy toy) {
        toyQueue.add(toy);
    }

    public String getToy() {
        Random rand = new Random();
        int chance = rand.nextInt(100);
        int cumulativeFrequency = 0;
        for (Toy toy : toyQueue) {
            cumulativeFrequency += toy.getFrequency();
            if (chance < cumulativeFrequency) {
                return toy.getId() + ": " + toy.getName();
            }
        }
        return "Игрушка не найдена";
    }

    public void saveToysToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Toy toy : toyQueue) {
                writer.write(toy.getId() + " " + toy.getName() + " " + toy.getFrequency());
                writer.newLine();
            }
            System.out.println("Игрушки успешно сохранены в файл: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadToysFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    int frequency = Integer.parseInt(parts[2]);
                    addToy(new Toy(id, name, frequency));
                }
            }
            System.out.println("Игрушки успешно загружены из файла: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listToys() {
        if (toyQueue.isEmpty()) {
            System.out.println("Игрушек нет.");
        } else {
            System.out.println("Список игрушек:");
            for (Toy toy : toyQueue) {
                System.out.println(toy.getId() + ": " + toy.getName() + " (Частота: " + toy.getFrequency() + ")");
            }
        }
    }
}
