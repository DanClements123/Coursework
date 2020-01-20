package Controllers;
import Server.Main;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Questions/")
public class Questions {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listThings() {
        System.out.println("thing/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT questionID, question, correctAnswer, wrongAnswer, wrongAnswer2, wrongAnswer3, topicID, quizName from Questions");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("questionID", results.getInt(1));
                item.put("question", results.getString(2));
                item.put("correctAnswer", results.getString(3));
                item.put("wrongAnswer1", results.getString(4));
                item.put("wrongAnswer2", results.getString(5));
                item.put("wrongAnswer3", results.getString(6));
                item.put("topicID", results.getInt(7));
                item.put("quizName", results.getString(8));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("/quizName")
    @Produces(MediaType.APPLICATION_JSON)
    public String quizName(@CookieParam("token") String token) {
        System.out.println("quizName/list");
        String un = "";

        JSONArray list = new JSONArray();
        try {
            PreparedStatement user = Main.db.prepareStatement("SELECT userName from loginData WHERE Token=?");
            user.setString(1,token);
            ResultSet users = user.executeQuery();

            PreparedStatement ps = Main.db.prepareStatement("SELECT quizName, buttonSelection, userName from Questions WHERE userName=?");
            ps.setString(1,users.getString("userName"));
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("quizName", results.getString(1));
                item.put("buttonSelection", results.getInt(2));
                item.put("userName", results.getString(3));
                list.add(item);
            }
            System.out.println(list.toString());
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }



    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("id") Integer id) throws Exception {
        if (id == null) {
            throw new Exception("Thing's 'id' is missing in the HTTP request's URL.");
        }
        System.out.println("thing/get/" + id);
        JSONObject item = new JSONObject();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT question FROM Questions WHERE questionID = ? ");
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("id", id);
                item.put("question", results.getString(1));
            }
            return item.toString();

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }


    @POST
    @Path("insert")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertThing(@FormDataParam("question") String question, @FormDataParam("correctAnswer") String correctAnswer,
                              @FormDataParam("wrongAnswer") String wrongAnswer, @FormDataParam("wrongAnswer2") String wrongAnswer2, @FormDataParam("wrongAnswer3") String wrongAnswer3, @CookieParam("token") String Token) {

        if (!User.validToken(Token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }

        try {
            if (question == null || correctAnswer == null || wrongAnswer == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Questions (question, correctAnswer, wrongAnswer, wrongAnswer2, wrongAnswer3) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, question);
            ps.setString(2, correctAnswer);
            ps.setString(3, wrongAnswer);
            ps.setString(4, wrongAnswer2);
            ps.setString(5, wrongAnswer3);
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
    public String updateThing(@FormDataParam("question") String question, @FormDataParam("correctAnswer") String correctAnswer,
                              @FormDataParam("wrongAnswer") String wrongAnswer, @FormDataParam("wrongAnswer2") String wrongAnswer2, @FormDataParam("wrongAnswer3") String wrongAnswer3, @CookieParam("token") String token) {

        if (!User.validToken(token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }

            try {
                if (question == null || correctAnswer == null || wrongAnswer == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/update=" + question);

                PreparedStatement ps = Main.db.prepareStatement("UPDATE Questions SET question = ?, correctAnswer = ?, wrongAnswer = ?, wrongAnswer2 = ?, wrongAnswer3 = ? WHERE questionID = ?");
                ps.setString(2, question);
                ps.setString(3, correctAnswer);
                ps.setString(4, wrongAnswer);
                ps.setString(5, wrongAnswer2);
                ps.setString(6, wrongAnswer3);
                return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to update item, please see server console for more information.\"}";
            }
        }

    //delete from database

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteThing(@FormDataParam("question") String question, @FormDataParam("correctAnswer") String correctAnswer,
                              @FormDataParam("wrongAnswer") String wrongAnswer, @FormDataParam("wrongAnswer2") String wrongAnswer2,
                              @FormDataParam("wrongAnswer3") String wrongAnswer3, @CookieParam("token") String token) {

        if (!User.validToken(token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }
            try {
                if (question == null || correctAnswer == null || wrongAnswer == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("thing/delete question=" + question);
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Questions WHERE question = ?");
                ps.setString(1, question);
                ps.execute();
                return "{\"status\": \"OK\"}";
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to delete item, please see server console for more information.\"}";
            }

        }

    }






