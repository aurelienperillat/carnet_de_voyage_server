package com.example.aurel.voyage;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//Path: http://localhost/<appln-folder-name>/publish
@Path("/publish")
public class Register {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/publish/trip
    @Path("/trip")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/publish/trip?titre=japon
    public String publish(@QueryParam("titre") String titre){
        String response = "";
       
        int retCode = registerTrip(titre);
        if(retCode > 0){
        	System.out.println("publishtrip : 1");
            response = Utility.constructJSON(retCode);
        }else if(retCode == -1){
        	System.out.println("publishtrip : -4");
            response = Utility.constructJSON("register",false, "You are already registered");
        }else if(retCode == -2){
        	System.out.println("publishtrip : -2");
            response = Utility.constructJSON("register",false, "Special Characters are not allowed in Username and Password");
        }else if(retCode == -3){
        	System.out.println("publishtrip : -3");
            response = Utility.constructJSON("register",false, "Error occured");
        }
        return response;
    }
    
 
 
    private int registerTrip(String titre){
        System.out.println("Inside checkCredentials");
        int result = -3;
        if(Utility.isNotNull(titre)){
            try {
                if((result = (int)DBConnection.insert(titre)) > 0){
                    System.out.println("RegisterTrip succeed");
                }
            } catch(SQLException sqle){
                System.out.println("RegisterTrip catch sqle");
                //When Primary key violation occurs that means user is already registered
                if(sqle.getErrorCode() == 1062){
                    result = -1;
                } 
                //When special characters are used in name,username or password
                else if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    result = -2;
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
                result = -3;
            }
        }else{
            System.out.println("Inside checkCredentials else");
            result = -3;
        }
 
        return result;
    }
    
 // HTTP Get Method
    @POST
    // Path: http://localhost/<appln-folder-name>/publish/trip
    @Path("/card")  
    // Produces JSON as response
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/publish/card?tripId=3&imgUri=C://
    public String publishCard(String jsonRequest){
        String response = "";
    	System.out.println(jsonRequest);
    	try {
			JSONObject json = new JSONObject(jsonRequest);
			int retCode = registerCard(json.getLong("tripId"), json.getString("filename"), json.getString("text"));
	        if(retCode == 0){
	            response = Utility.constructJSON("register",true);
	        }else if(retCode == 1){
	            response = Utility.constructJSON("register",false, "You are already registered");
	        }else if(retCode == 2){
	            response = Utility.constructJSON("register",false, "Special Characters are not allowed in Username and Password");
	        }else if(retCode == 3){
	            response = Utility.constructJSON("register",false, "Error occured");
	        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return response;
 
    }
    
    private int registerCard(long tripId, String filename, String text ){
        System.out.println("RegisterCArd :Inside checkCredentials");
        
        int result = 3;
            try {
                if(DBConnection.insert(tripId, filename, text)){
                    System.out.println("RegisterCard : succeed");
                    result = 0;
                }
            } catch(SQLException sqle){
                System.out.println("RegisterTrip catch sqle");
                //When Primary key violation occurs that means user is already registered
                if(sqle.getErrorCode() == 1062){
                    result = 1;
                } 
                //When special characters are used in name,username or password
                else if(sqle.getErrorCode() == 1064){
                    System.out.println("RegisterCard : "+sqle.getErrorCode());
                    result = 2;
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("ResgiterCard : Inside checkCredentials catch e ");
                result = 3;
            }
        
        return result;
    }
 
   
}
