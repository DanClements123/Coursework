package Controllers;

import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

    @Path("Results/")
    public class Results {

        //List all things

        @GET
        //retrieves data from the database
        @Path("list")
        //Path for testing the APIs in git bash
        @Produces(MediaType.APPLICATION_JSON)
        public String listItems() {
            System.out.println("Results/read");
            JSONArray list = new JSONArray();
            //Creates array in order to output results from the database
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT userID, grade FROM Results");
                //This statement selects which cells to pick from in order to output
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    JSONObject item = new JSONObject();
                    item.put("userID", results.getInt(1));
                    item.put("Grade", results.getString(3));
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

        //Insert into database


        //update database

        @POST
        @Path("new")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String updateThing(@FormDataParam("Grade") String Grade, @FormDataParam("gradeID") int gradeID) {
            try {
                if (Grade == null || gradeID == 0) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/update=" + Grade + gradeID);

                PreparedStatement ps = Main.db.prepareStatement("UPDATE Grade, gradeID SET Grade = ? gradeID = ? WHERE id = ?");
                ps.setInt(2, gradeID);
                ps.setString(3, Grade);
                return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
            }
        }
    }
