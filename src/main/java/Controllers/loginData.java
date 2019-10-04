package Controllers;
import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class loginData {
    @GET
    @Path("Users/loginData")
    @Produces(MediaType.APPLICATION_JSON)
    public String readLoginData() {
        System.out.println("loginData/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT loginID, userName, password FROM loginData");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject loginInfo = new JSONObject();
                loginInfo.put("loginID", results.getInt(1));
                loginInfo.put("userName", results.getString(2));
                loginInfo.put("password", results.getString(3));
                list.add(loginInfo);
                System.out.println(list);
            }
            return list.toString();


        } catch (Exception exception) {

            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";

        }


    }
}
public class loginDataInput {
    @POST
    @Path("Users/loginData")
    @Produces(MediaType.APPLICATION_JSON)
    public String inputLoginData(@FormDataParam("id") Integer id, @FormDataParam("name") String name, @FormDataParam("quantity") Integer quantity) {
            try {
                if (id == null || name == null || quantity == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/update id=" + id);

                PreparedStatement ps = Main.db.prepareStatement("UPDATE Things SET Name = ?, Quantitiy = ? WHERE Id = ?");
                ps.setString(1, name);
                ps.setInt(2, quantity);
                ps.setInt(3, id);
                ps.execute();
                return "{\"status\": \"OK\"}";
            } catch (Exception exception ){
                System.out.println("Datbase error: " + exception.getMessage());
                return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
            }
        }
}
