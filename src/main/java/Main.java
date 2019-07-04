import org.sqlite.SQLiteConfig;
import java.sql.*;
import java.util.Scanner;


public class Main {

    public static Connection db = null;


    private static void openDatabase(String dbFile) {

        try {

            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established");

        } catch (Exception exception) {

            System.out.println("Database connection error: " + exception.getMessage());

        }

    }

    private static void closeDatabase() {

        try {

            db.close();
            System.out.println("Disconnected from database.");

        } catch (Exception exception) {

            System.out.println("Database disconnection error: " + exception.getMessage());

        }


    }

    public static void main(String[] args) {

        openDatabase("Users.db");

        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String userNameInput = input.nextLine();
        System.out.println("Enter your user ID number: ");
        int userIdInput = input.nextInt();


        try {

            PreparedStatement ps = db.prepareStatement("INSERT INTO userInfo (userID, userName) VALUES (?, ?)");

            ps.setInt(1, userIdInput);
            ps.setString(2, userNameInput);
            ps.executeUpdate();

            //code using the database goes here


        } catch (Exception exception){
            System.out.println("Database disconnection error: " + exception.getMessage());
        }

        try {

            PreparedStatement ps = db.prepareStatement("SELECT userID, userName FROM userInfo");

            ResultSet results = ps.executeQuery();
            while(results.next()){

                int userID = results.getInt(1);
                String username = results.getString(2);
                System.out.println(userID + " " + username);

            }


        } catch (Exception exception){

            System.out.println("Database error: " + exception.getMessage());

        }

        closeDatabase();
    }
}














































