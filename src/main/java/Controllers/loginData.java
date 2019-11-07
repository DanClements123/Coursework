package Controllers;
import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class loginData {

    //List all things

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listItems() {
        System.out.println("loginData/read");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT loginId, password, userName FROM loginData");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("id", results.getInt(1));
                item.put("name", results.getString(2));
                item.put("quantity", results.getInt(3));
                list.add(item);
            }
            return list.toString();

        } catch (Exception exception) {

            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";

        }


    }

    //list one thing(s)

    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String retrieveItems(@PathParam("id") Integer id) throws Exception {
        if (id == null) {
            throw new Exception("Thing's 'id' is missing in the HTTP request's URL.");
        }
        System.out.println("loginData/get/" + id);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT userName, password WHERE loginId = 1000");
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("loginId", id);
                item.put("name", results.getString(1));
                item.put("quantity", results.getInt(2));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

    //Insert into database

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertThing(@FormDataParam("userName") String userName, @FormDataParam("password") String password) {
        try {
            if (userName == null || password == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Things (userName, password) VALUES (?, ?)");
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
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateThing(@FormDataParam("userName") String userName, @FormDataParam("password") String password) {
        try {
            if (userName == null || password == null) {
            throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/update=" + userName);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE loginData SET userName = ?, password = ? WHERE id = ?");
            ps.setString(2, userName);
            ps.setString(3, password);
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

} // end of file
