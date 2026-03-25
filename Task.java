package org.studyeasy;

import java.io.Serializable;

public class Task implements Comparable<Task>, Serializable {

    private int id;
    private String title;
    private int priority;
    private String deadline;

    public Task(int id, String title, int priority, String deadline) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority); // High priority first
    }

    @Override
    public String toString() {
        return id + "," + title + "," + priority + "," + deadline;
    }

    public static Task fromString(String line) {
        String[] parts = line.split(",");
        return new Task(
                Integer.parseInt(parts[0]),
                parts[1],
                Integer.parseInt(parts[2]),
                parts[3]
        );
    }
}