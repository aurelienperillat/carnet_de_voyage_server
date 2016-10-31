package com.example.aurel.voyage;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
 
public class Utility {
    /**
     * Null check Method
     * 
     * @param txt
     * @return
     */
    public static boolean isNotNull(String txt) {
        // System.out.println("Inside isNotNull");
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }
 
    /**
     * Method to construct JSON
     * 
     * @param tag
     * @param status
     * @return
     */
    public static String constructJSON(String tag, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
 
    public static String constructJSON(long id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("newId", id);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    /**
     * Method to construct JSON with Error Msg
     * 
     * @param tag
     * @param status
     * @param err_msg
     * @return
     */
    public static String constructJSON(String tag, boolean status,String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
 
    public static String constructJSON(ArrayList<Trip> tripList) {
    	JSONArray jarray = new JSONArray();
    	JSONObject mainObj = new JSONObject();
    	try{
    		for(Trip trip : tripList){
    			System.out.println("Utility : "+trip.getId() + " " + trip.getTitre());
    			
    			JSONObject obj = new JSONObject();
    			obj.put("id", trip.getId());
    			obj.put("titre", trip.getTitre());
    			jarray.put(obj);
    		}    		
        	mainObj.put("trip", jarray);
    	}catch(JSONException e) {}
    	return mainObj.toString();
    }
    
    public static String constructJSONCard(ArrayList<Card> cardList) {
    	JSONArray jarray = new JSONArray();
    	JSONObject mainObj = new JSONObject();
    	try{
    		for(Card card : cardList){
    			System.out.println("Utility " + card.getId() + " " + card.getTripId() + " " + card.getFilename() + " " + card.getText());
    			
    			JSONObject obj = new JSONObject();
    			obj.put("id", card.getId());
    			obj.put("tripId", card.getTripId());
    			obj.put("filename", card.getFilename());
    			obj.put("text", card.getText());
    			jarray.put(obj);
    		}    		
        	mainObj.put("card", jarray);
    	}catch(JSONException e) {}
    	return mainObj.toString();
    }
}
