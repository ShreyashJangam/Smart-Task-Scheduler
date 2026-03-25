package org.studyeasy;

import java.sql.*;
        import java.util.Scanner;

public class InventoryApp {

    static final String URL = "jdbc:sqlite:inventory.db";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        createTable();

        while (true) {
            System.out.println("\n1.Add  2.View  3.Update  4.Delete  5.Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // CREATE TABLE
    static void createTable() {
        try (Connection con = DriverManager.getConnection(URL);
             Statement stmt = con.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "quantity INTEGER, " +
                    "price REAL)";

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ADD
    static void addProduct() {
        try (Connection con = DriverManager.getConnection(URL)) {

            String sql = "INSERT INTO products VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("ID: ");
            ps.setInt(1, sc.nextInt());

            System.out.print("Name: ");
            ps.setString(2, sc.next());

            System.out.print("Quantity: ");
            ps.setInt(3, sc.nextInt());

            System.out.print("Price: ");
            ps.setDouble(4, sc.nextDouble());

            ps.executeUpdate();
            System.out.println("Product added");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW
    static void viewProducts() {
        try (Connection con = DriverManager.getConnection(URL);
             Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM products");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getInt("quantity") + " " +
                                rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    static void updateProduct() {
        try (Connection con = DriverManager.getConnection(URL)) {

            String sql = "UPDATE products SET quantity=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Enter ID: ");
            ps.setInt(2, sc.nextInt());

            System.out.print("New Quantity: ");
            ps.setInt(1, sc.nextInt());

            ps.executeUpdate();
            System.out.println("Updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    static void deleteProduct() {
        try (Connection con = DriverManager.getConnection(URL)) {

            String sql = "DELETE FROM products WHERE id=?";
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