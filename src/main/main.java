package main;

import config.config;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        config.connectDB();

        config conf = new config();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Add T-Shirt");
            System.out.println("2. View T-Shirts");
            System.out.println("3. Update T-Shirt");
            System.out.println("4. Delete T-Shirt");
            System.out.println("5. Add Order");
            System.out.println("6. View Orders");
            System.out.println("7. Update Order Status");
            System.out.println("8. Add Customer");
            System.out.println("9. View Customers");
            System.out.println("10. Update Customer");
            System.out.println("11. Delete Customer");
            System.out.println("12. Exit");
            System.out.println("==========================");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Enter Brand: ");
                    String brand = sc.nextLine();
                    System.out.print("Enter Size: ");
                    String size = sc.nextLine();
                    System.out.print("Enter Color/Design: ");
                    String color = sc.nextLine();
                    conf.addRecord("INSERT INTO TShirt (brand, size, color_design) VALUES (?, ?, ?)", brand, size, color);
                    break;
                }

                case 2: {
                    conf.viewRecords("SELECT * FROM TShirt",
                            new String[]{"ID","Brand","Size","Color/Design"},
                            new String[]{"id","brand","size","color_design"});
                }

                case 3: {
                    System.out.print("Enter T-Shirt ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new Brand: ");
                    String newBrand = sc.nextLine();
                    System.out.print("Enter new Size: ");
                    String newSize = sc.nextLine();
                    System.out.print("Enter new Color/Design: ");
                    String newColor = sc.nextLine();
                    conf.updateRecord("UPDATE TShirt SET brand=?, size=?, color_design=? WHERE id=?",
                            newBrand,newSize,newColor,updateId);
                    break;
                }

                case 4: {
                    System.out.print("Enter T-Shirt ID to delete: ");
                    int deleteId = sc.nextInt();
                    conf.deleteRecord("DELETE FROM TShirt WHERE id=?", deleteId);
                    break;
                }

                case 5: {
                    System.out.print("Enter Customer ID: ");
                    int customerId = sc.nextInt();
                    System.out.print("Enter T-Shirt ID: ");
                    int tshirtId = sc.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();
                    System.out.print("Enter Total Price: ");
                    double totalPrice = sc.nextDouble();
                    sc.nextLine();
                    conf.addRecord("INSERT INTO orders (customer_id, tshirt_id, quantity, total_price, status) VALUES (?, ?, ?, ?, 'Pending')",
                            customerId,tshirtId,quantity,totalPrice);
                    break;
                }

                case 6: {
                    conf.viewRecords(
                            "SELECT o.order_id, o.customer_id, t.brand, t.size, t.color_design, o.quantity, o.total_price, o.order_date, o.status " +
                                    "FROM orders o JOIN TShirt t ON o.tshirt_id = t.id",
                            new String[]{"Order ID","Customer ID","Brand","Size","Color/Design","Quantity","Total Price","Order Date","Status"},
                            new String[]{"order_id","customer_id","brand","size","color_design","quantity","total_price","order_date","status"});
                }

                case 7: {
                    System.out.print("Enter Order ID to update: ");
                    int orderId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new status (Pending / Completed / Canceled): ");
                    String newStatus = sc.nextLine();
                    conf.updateRecord("UPDATE orders SET status=? WHERE order_id=?", newStatus, orderId);
                    break;
                }

                case 8: {
                    System.out.print("Enter First Name: ");
                    String firstName = sc.nextLine();
                    System.out.print("Enter Last Name: ");
                    String lastName = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter Contact: ");
                    String contact = sc.nextLine();
                    System.out.print("Enter Address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter Status (Active/Inactive): ");
                    String status = sc.nextLine();
                    conf.addRecord("INSERT INTO customer (first_name, last_name, email, contact, address, status) VALUES (?, ?, ?, ?, ?, ?)",
                            firstName,lastName,email,contact,address,status);
                    break;
                }

                case 9: {
                    conf.viewRecords("SELECT * FROM customer",
                            new String[]{"Customer ID","First Name","Last Name","Email","Contact","Address","Status"},
                            new String[]{"customer_id","first_name","last_name","email","contact","address","status"});
                }

                case 10: {
                    System.out.print("Enter Customer ID to update: ");
                    int custId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new First Name: ");
                    String newF = sc.nextLine();
                    System.out.print("Enter new Last Name: ");
                    String newL = sc.nextLine();
                    System.out.print("Enter new Email: ");
                    String newE = sc.nextLine();
                    System.out.print("Enter new Contact: ");
                    String newC = sc.nextLine();
                    System.out.print("Enter new Address: ");
                    String newA = sc.nextLine();
                    System.out.print("Enter new Status: ");
                    String newS = sc.nextLine();
                    conf.updateRecord("UPDATE customer SET first_name=?, last_name=?, email=?, contact=?, address=?, status=? WHERE customer_id=?",
                            newF,newL,newE,newC,newA,newS,custId);
                    break;
                }

                case 11: {
                    System.out.print("Enter Customer ID to delete: ");
                    int custDelete = sc.nextInt();
                    conf.deleteRecord("DELETE FROM customer WHERE customer_id=?", custDelete);
                    break;
                }

                case 12: {
                    System.out.println("Exiting program...");
                    break;
                }

                default: {
                    System.out.println("Invalid choice. Please try again.");
                    break;
                }
            }

        } while (choice != 12);
    }
}
