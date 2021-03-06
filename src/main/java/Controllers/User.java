package Controllers;


import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("user/")
    public class User {

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("username") String username, @FormDataParam("password") String password) {

        try {

            System.out.println("user/login");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password FROM loginData WHERE userName = ?");
            ps1.setString(1, username);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {
                String correctPassword = loginResults.getString(1);

                if (password.equals(correctPassword)) {
                    String token = UUID.randomUUID().toString();

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE loginData SET Token = ? WHERE Username = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, username);
                    ps2.executeUpdate();

                    JSONObject userDetails = new JSONObject();
                    userDetails.put("username", username);
                    userDetails.put("token", token);
                    return userDetails.toString();

                } else {
                    return "{\"error\": \"Incorrect password!\"}";
                }

            } else {
                return "{\"error\": \"Unknown user!\"}";
            }

        } catch (Exception exception) {
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

        @POST
        @Path("logout")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String logoutUser (@CookieParam("token") String Token) {

            try {

                System.out.println("user/logout");

                PreparedStatement ps1 = Main.db.prepareStatement("SELECT loginID FROM loginData WHERE Token = ?");
                ps1.setString(1, Token);
                ResultSet logoutResults = ps1.executeQuery();
                if (logoutResults.next()) {

                    int id = logoutResults.getInt(1);

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE loginData SET Token = NULL WHERE loginID = ?");
                    ps2.setInt(1, id);
                    ps2.executeUpdate();

                    return "{\"status\": \"OK\"}";
                } else {

                    return "{\"error\": \"Invalid token!\"}";

                }

            } catch (Exception exception) {
                System.out.println("Database error during /user/logout: " + exception.getMessage());
                return "{\"error\": \"Server side error!\"}";
            }
        }


            public static boolean validToken(String token){
                try {
                    PreparedStatement ps = Main.db.prepareStatement("SELECT loginID FROM loginData WHERE Token = ?");
                    ps.setString(1, token);
                    ResultSet logoutResults = ps.executeQuery();
                    return logoutResults.next();
                } catch (Exception exception) {
                    System.out.println("Database error during /user/logout: " + exception.getMessage());
                    return false;
                }
    }
} //end of file







