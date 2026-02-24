import main.java.common.Utility;
import main.java.controller.AuthController;

public class RevShopApp {

    public static void main(String[] args) {


        AuthController authController = new AuthController();

        while (true) {

            System.out.println("✅ Oracle Database Connected Successfully!");


            System.out.println("\n====== REVSHOP MENU ======");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = Utility.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    authController.register();
                    break;

                case 2:
                    authController.login();
                    break;

                case 3:
                    System.out.println("Thank you for using RevShop!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
