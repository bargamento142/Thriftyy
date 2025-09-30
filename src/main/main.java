package main;

import config.config;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int option;
        boolean run = true;
        String response;

        do {
            System.out.println("\n===== THRIFTY T-SHIRT SYSTEM =====");
            System.out.println("1. Add T-Shirt");
            System.out.println("2. View T-Shirts");
            System.out.println("3. Update T-Shirt");
            System.out.println("4. Delete T-Shirt");
            System.out.println("5. Exit");
            
            
          
            System.out.print("Choose an option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1: //ADD
                    System.out.print("\nBrand: ");
                    String brand = sc.nextLine();
                    System.out.print("Size: ");
                    String size = sc.nextLine();
                    System.out.print("Color/Design: ");
                    String color = sc.nextLine();

                    String sqlAdd = "INSERT INTO TShirt (brand, size, color_design) VALUES (?, ?, ?)";
                    conf.addRecord(sqlAdd, brand, size, color);
                    break;

                case 2: //VIEW
                    String sqlView = "SELECT id, brand, size, color_design FROM TShirt";
                    String[] headers = {"ID", "Brand", "Size", "Color/Design"};
                    String[] columns = {"id", "brand", "size", "color_design"};
                    conf.viewRecords(sqlView, headers, columns);
                    break;
                  
                case 3: //UPDATE 
                  System.out.print("\nEnter T-Shirt ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                  System.out.print("New Brand: ");
                  String newBrand = sc.nextLine();
                  System.out.print("New Size: ");
                  String newSize = sc.nextLine();
                  System.out.print("New Color/Design: ");
                  String newColor = sc.nextLine();

                     
               case 4: // DELETE
                    System.out.print("\nEnter T-Shirt ID to update: ");
                      int deleteId = sc.nextInt();
                 String sqlDelete = "DELETE FROM TShirt WHERE id=?";
                  conf.deleteRecord(sqlDelete, deleteId);
                  break;       
                    
                case 5: // EXIT
                    run = false;
                    break;
                    
                default:
               System.out.println("Invalid Option. Please Try Again");
                    
            }

            if (run) {
                System.out.print("\nDo you want to continue? (yes/no): ");
                response = sc.next();
            } else {
                response = "no";
            }

        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank you for using the Thrifty T-Shirt System!");
    }
}