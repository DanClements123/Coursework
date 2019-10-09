package Controllers;
import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class loginData {
    @GET
    @Path("loginData/read/")
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

} // end of file
