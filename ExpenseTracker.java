package org.studyeasy;

import java.sql.*;
import java.util.Scanner;

public class ExpenseTracker {

    static final String URL = "jdbc:sqlite:expense.db";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        createTable();

        while (true) {
            System.out.println("\n1.Add  2.View  3.Monthly Total  4.Category Total  5.Delete  6.Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> monthlyTotal();
                case 4 -> categoryTotal();
                case 5 -> deleteExpense();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // CREATE TABLE
    static void createTable() {
        try (Connection con = DriverManager.getConnection(URL);
             Statement stmt = con.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                    "id INTEGER PRIMARY KEY, " +
                    "category TEXT, " +
                    "amount REAL, " +
                    "date TEXT)";

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ADD
    static void addExpense() {
        try (Connection con = DriverManager.getConnection(URL)) {

            String sql = "INSERT INTO expenses VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("ID: ");
            ps.setInt(1, sc.nextInt());
            sc.nextLine();

            System.out.print("Category: ");
            ps.setString(2, sc.nextLine());

            System.out.print("Amount: ");
            ps.setDouble(3, sc.nextDouble());
            sc.nextLine();

            System.out.print("Date (YYYY-MM): ");
            ps.setString(4, sc.nextLine());

            ps.executeUpdate();
            System.out.println("Expense added");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW
    static void viewExpenses() {
        try (Connection con = DriverManager.getConnection(URL);
             Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM expenses");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("category") + " | " +
                                rs.getDouble("amount") + " | " +
                                rs.getString("date")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MONTHLY TOTAL
    static void monthlyTotal() {
        try (Connection con = DriverManager.getConnection(URL)) {

            System.out.print("Enter Month (YYYY-MM): ");
            sc.nextLine();
            String month = sc.nextLine();

            String sql = "SELECT SUM(amount) FROM expenses WHERE date=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, month);

            ResultSet rs = ps.executeQuery();

            System.out.println("Total: ₹" + rs.getDouble(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CATEGORY TOTAL
    static void categoryTotal() {
        try (Connection con = DriverManager.getConnection(URL)) {

            String sql = "SELECT category, SUM(amount) FROM expenses GROUP BY category";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(1) + " : ₹" + rs.getDouble(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    static void deleteExpense() {
        try (Connection con = DriverManager.getConnection(URL)) {

            String sql = "DELETE FROM expenses WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter ID: ");
            ps.setInt(1, sc.nextInt());

            ps.executeUpdate();
            System.out.println("Deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}