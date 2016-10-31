package com.example.aurel.voyage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
public class DBConnection {
    /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constants.dbClass);
            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
        } catch (Exception e) {
            throw e;
        } finally {
            return con;
        }
    }
       
    public static ArrayList<Trip> queryTrip(String selection, String selectionArgs, String selectionOrder) throws Exception {
    	ArrayList<Trip> tripList = new ArrayList<Trip>();
    
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Statement stmt = dbConn.createStatement();
            
            String query = "SELECT * FROM trip";
            if(selection != null){
            	query = "SELECT " + selection + " FROM trip";
            }
            if(selectionArgs != null) {
            	query = query + " WHERE " + selectionArgs;
            }
            if(selectionOrder != null) { 
            	query = query + " ORDER BY " + selectionOrder;
            }
            
            ResultSet rs = stmt.executeQuery(query);
                   
            if(rs.first()) {
                do {
                	long id = rs.getLong(rs.findColumn("id"));
                	String titre = rs.getString(rs.findColumn("titre"));
                	tripList.add(new Trip(id, titre));
                	System.out.println(id + " " + titre);
                }while(rs.next());
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return tripList;
    }
    
    public static ArrayList<Card> queryCard(String selection, String selectionArgs, String selectionOrder) throws Exception {
    	ArrayList<Card> cardList = new ArrayList<Card>();
    
        Connection dbConn = null;
        try {
            try {
            	System.out.println("db : ");
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Statement stmt = dbConn.createStatement();
            
            String query = "SELECT * FROM card";
            if(selection != null){
            	query = "SELECT " + selection + " FROM card";
            }
            if(selectionArgs != null) {
            	query = query + " WHERE " + selectionArgs;
            }
            if(selectionOrder != null) { 
            	query = query + " ORDER BY " + selectionOrder;
            }
            
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("db : "+rs.toString());      
            if(rs.first()) {
                do {
                	long id = rs.getLong(rs.findColumn("id"));
                	long tripId = rs.getLong(rs.findColumn("tripId"));
                	String filename = rs.getString(rs.findColumn("fielname"));
                	String text = rs.getString(rs.findColumn("description"));
                	cardList.add(new Card(id, tripId, filename, text));
                	System.out.println("db : " + id + " " + tripId + " " + filename + " " + text);
                }while(rs.next());
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return cardList;
    }
    
    public static long insert(String titre) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        long id = 0;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "INSERT INTO `trip` (`id`, `titre`) VALUES (NULL, '"+ titre + "')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
                query = "SELECT * FROM `trip`";
                ResultSet rs = stmt.executeQuery(query);
                
                if(rs.first()) {
                	System.out.println("deb : inside insert titre");
                    do {
                    	if(rs.getString(rs.findColumn("titre")).equals(titre)) {
                    		id = rs.getLong(rs.findColumn("id"));
                    		System.out.println("insert : "+id);
                    	}
                    }while(rs.next());
                }
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return id;
    }
    
    public static boolean insert(long tripId, String filename, String text) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "INSERT INTO `card` (`id`, `tripId`, `fielname`, `description`) VALUES (NULL, '"+ tripId + "', '"+ filename +"', '"+ text +"')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
}
