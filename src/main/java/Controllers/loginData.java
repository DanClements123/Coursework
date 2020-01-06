package Controllers;
import Server.Main;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("loginData/")
public class loginData {

    //List all things

    @GET //fully tested
    //retrieves data from the database
    @Path("list")
    //Path for testing the APIs in git bash
    @Produces(MediaType.APPLICATION_JSON)
    public String listItems() {
        System.out.println("loginData/read");
        JSONArray list = new JSONArray();
        //Creates array in order to output results from the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT loginID, userName, password FROM loginData");
            //This statement selects which cells to pick from in order to output
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("loginID", results.getInt(1));
                item.put("userName", results.getString(2));
                item.put("password", results.getString(3));
                list.add(item);
                //Adds items to the JSONArray 'list'
            }
            return list.toString();

        } catch (Exception exception) {

            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
            //Outputs any errors if they occur

        }


    }

    //list one thing(s)

    @GET
    //Retrieves data from the database
    @Path("get/{id}")
    //Path variable which allows the data to be outputted using git bash in JSON format
    @Produces(MediaType.APPLICATION_JSON)
    public String retrieveItems(@PathParam("id") Integer id) throws Exception {
        if (id == null) {
            throw new Exception("Thing's 'id' is missing in the HTTP request's URL.");
            //If there is an error in the HTTP request it is outputted here, promoting the user to re-enter the data
        }
        System.out.println("loginData/get/" + id);
        JSONObject item = new JSONObject();
        //Creates JSONArray to be used as response from the git bash test results
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT userName FROM loginData WHERE loginID = ?");
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("loginID", id);
                item.put("userName", results.getString(1));
                //Outputs data into the git bash response, allowing the response to be tested against the sample data
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
            //outputs any errors which occur from database information or the users errors
        }
    }

    //Insert into database

    @POST
    @Path("insert")
    //The @POST allows the information to be inputted into the database and the @PATH signifies the path which the user must specify when testing the JSON response
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertThing(@FormDataParam("userName") String userName, @FormDataParam("password") String password, @CookieParam("token") String Token) {

        if (!User.validToken(Token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }
                // Specifies the users data which is going to be inputting into the database
        try {
            if (userName == null || password == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request."); //throws exception into the console if the users database
                // - inputs were incorrect or did not meet the parameters of the database
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO loginData (userName, password) VALUES (?, ?)");
            //Checks data against the data stored into the database, allowing the user access to the account if correct
            ps.setString(1, userName);
            ps.setString(2, password);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\":\"Unable to create new item, please see server console for more information.\"}";
        }
    }

    //update database

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateThing(@FormDataParam("userName") String userName, @FormDataParam("password") String password, @FormDataParam("loginID") Integer loginID, @CookieParam("token") String Token) {

        if (!User.validToken(Token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }
        try {
            if (userName == null || password == null) {
            throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update=" + loginID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE loginData SET userName = ?, password = ? WHERE loginID = ?");
            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setInt(3, loginID);
            ps.execute();
            return "{\"Status\": \"OK\"}";
        } catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
        }
    }

    //delete from database

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteThing(@FormDataParam("userName") String userName){
        try{
            if (userName == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete userName=" + userName);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM loginData WHERE userName = ?");
            ps.setString(1, userName);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more information.\"}";
        }

    }

    //login to database

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("userName") String userName, @FormDataParam("password") String password, @CookieParam("token") String Token) {

        if (!User.validToken(Token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }

        try {

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT password FROM loginData WHERE userName = ?");
            ps1.setString(1, userName);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {
                String correctPassword = loginResults.getString(1);
                if (password.equals(correctPassword)) {
                    String token = UUID.randomUUID().toString();
                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE loginData SET Token = ? WHERE userName = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, userName);
                    ps2.executeUpdate();

                    return "{\"token\": \""+ token + "\"}";
                } else {
                    return "{\"error\": \"Incorrect password!\"}";
                }
            } else {
                return "{\"error\": \"Unknown user!\"}";
            }
        }catch (Exception exception){
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }


} // end of file
