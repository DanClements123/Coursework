package Controllers;
import Server.Main;


import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("userInfo/")
public class userInfo {
        //List all things

        @GET
        //retrieves data from the database
        @Path("list")
        //Path for testing the APIs in git bash
        @Produces(MediaType.APPLICATION_JSON)
        public String listItems() {
            System.out.println("userInfo/read");
            JSONArray list = new JSONArray();
            //Creates array in order to output results from the database
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT userName, firstName, lastName FROM userInfo");
                //This statement selects which cells to pick from in order to output
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    JSONObject item = new JSONObject();
                    item.put("userName", results.getString(1));
                    item.put("firstName", results.getString(2));
                    item.put("surename", results.getString(3));
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
            System.out.println("userName/get/" + id);
            JSONObject item = new JSONObject();
            //Creates JSONArray to be used as response from the git bash test results
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT userName FROM userInfo WHERE id = ?");
                ps.setInt(1, id);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    item.put("loginID", id);
                    item.put("userName", results.getString(2));
                    //Outputs data into the git bash response, allowing the response to be tested against the sample data
                }
                return item.toString();
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
                //outputs any errors which occur from database information or the users errors
            }
        }



        //update database

        @POST
        @Path("new")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String updateThing(@FormDataParam("userName") String userName, @FormDataParam("firstName") String firstName, @FormDataParam("lastName") String lastName, @CookieParam("token") String Token) {

            if (!User.validToken(Token)) {
                return "{\"error\": \"You don't appear to be logged in.\"}";
            }
            try {
                if (userName == null || firstName == null || lastName == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/update=" + userName);

                PreparedStatement ps = Main.db.prepareStatement("UPDATE userInfo SET userName = ?, firstName = ?, lastName = ? WHERE id = ?");
                ps.setString(2, userName);
                ps.setString(3, firstName);
                ps.setString(4, lastName);
                return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
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
        public String deleteThing(@FormDataParam("userName") String userName,
                                  @FormDataParam("firstName") String firstName,
                                  @FormDataParam("lastName") String lastName, @CookieParam("token") String Token) {

            if (!User.validToken(Token)) {
                return "{\"error\": \"You don't appear to be logged in.\"}";
            }
            try{
                if (userName == null || firstName == null || lastName == null){
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/delete userName=" + userName);
                PreparedStatement ps = Main.db.prepareStatement("DELETE userInfo WHERE userName = ?, firstName = ?, lastName = ?");
                ps.setString(2, userName);
                ps.setString(3, firstName);
                ps.setString(4, lastName);
                ps.execute();
                return "{\"status\": \"OK\"}";
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to delete item, please see server console for more information.\"}";
            }

        }
    }


