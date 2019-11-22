package Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.*;



public class Main {

    //----------------------------------------------------------------

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

    //----------------------------------------------------------------


    public static void main(String[] args) {

            openDatabase("Users.db");

            ResourceConfig config = new ResourceConfig();
            config.packages("Controllers");
            config.register(MultiPartFeature.class);
            ServletHolder servlet = new ServletHolder(new ServletContainer(config));

            Server server = new Server(8081);
            ServletContextHandler context = new ServletContextHandler(server, "/");
            context.addServlet(servlet, "/*");

            try {
                server.start();
                System.out.println("Server successfully started.");
                server.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        closeDatabase();
    }
}

//curl test lines:
//curl -s localhost:8081/thing/get/
//curl -s localhost:8081/apipostpath -F id=1 -F name=Steve
//curl -s localhost:8081/apipostpath -F message="Hello there everyone!"
//curl -s localhost:8081/apipath --cookie token=abcdef123456













































