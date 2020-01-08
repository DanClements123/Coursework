package Controllers;
import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("quizTopics/")
public class quizTopics {

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
            PreparedStatement ps = Main.db.prepareStatement("SELECT Topic FROM quizTopics WHERE topicID = ?");
            ps.setInt(1,  id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("loginID", id);
                item.put("Topic", results.getString(2));
                //Outputs data into the git bash response, allowing the response to be tested against the sample data
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
            //outputs any errors which occur from database information or the users errors
        }
    }
}




