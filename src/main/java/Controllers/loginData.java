package Controllers;
import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class loginData {
    @GET
    @Path("Users/")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginData() {
        System.out.println("loginData/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Id, Name, Quantity FROM Things");
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
}
















