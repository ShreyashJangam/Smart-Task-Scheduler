package org.studyeasy;

import java.io.*;
import java.util.*;

public class TaskScheduler {

    static PriorityQueue<Task> tasks = new PriorityQueue<>();
    static Scanner scanner = new Scanner(System.in);
    // Standard constant declaration
    static final String FILE = "C:\\Users\\Shreyash\\OneDrive\\Documents\\Task.txt";


    public static void main(String[] args) {
        loadTasks();

        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Remove Highest Priority Task");
            System.out.println("4. Save & Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> removeTask();
                case 4 -> {
                    saveTasks();
                    System.out.println("Saved. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addTask() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Priority (1-5): ");
        int priority = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Deadline: ");
        String deadline = scanner.nextLine();

        tasks.add(new Task(id, title, priority, deadline));
        System.out.println("Task added successfully!");
    }

    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (Task t : tasks) {
            System.out.println(t);
        }
    }

    static void removeTask() {
        Task removed = tasks.poll();
        if (removed != null)
            System.out.println("Removed: " + removed);
        else
            System.out.println("No tasks to remove.");
    }

    static void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Task t : tasks) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadTasks() {
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(Task.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}