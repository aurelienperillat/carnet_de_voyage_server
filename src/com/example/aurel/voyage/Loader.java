package com.example.aurel.voyage;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/login
@Path("/load")
public class Loader {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/trip")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 

    public String Load(){
        String response = ""; 
        try{
        	response = Utility.constructJSON(DBConnection.queryTrip(null,null,null));
        }catch(SQLException sqle) { }
         catch(Exception e){ }
        
        if(response == null || response == "") {
        	response = Utility.constructJSON("load", false);
        }    
        return response;        
    }
    
 // HTTP Get Method    
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/card")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON)
    public String Load(@QueryParam("tripId") long tripId){
    	System.out.println("card load : "+tripId);
        String response = ""; 
        try{
        	response = Utility.constructJSONCard(DBConnection.queryCard(null,"tripId = "+tripId,null));
        }catch(SQLException sqle) { }
         catch(Exception e){ }
        
        if(response == null || response == "") {
        	response = Utility.constructJSON("load", false);
        }    
        return response;        
    }
 
}
