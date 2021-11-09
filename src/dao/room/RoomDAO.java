/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.room;

import Common.Common;
import control.Room;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asus
 */
public class RoomDAO {
    private String jdbcUrl = Common.jdbcUrl;
    private String jdbcUsername = Common.jdbcUsername;
    private String jdbcPassword = Common.jdbcPassword;
    
    private static String CREATE_ROOM = "INSERT INTO room " + 
            "(roomname) VALUES " + "(?)";
    private static String ADD_ROOM_CONNECTION = "INSERT INTO roomConnection (roomID, userID)" +
            "VALUES (?, ?)";
    private static String DELETE_ROOM_CONNECTION = "DELETE FROM roomConnection where roomID = ? AND userID = ?";
    private static String SELECT_ROOM_MEMBER_BY_ROMID = "SELECT userID from roomConnection where roomID = ?";
    private static String SELECT_ROOM_BY_NAME = "SELECT * FROM room WHERE roomName = ?";
    private static String SELECT_ROOM_BY_ID = "SELECT * FROM room WHERE id = ?";
    private static String UPDATE_ROOM_NAME = "UPDATE room SET roomName = ? Where id = ?";

    public RoomDAO() {
    }
    
    private Connection getConnection(){
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Drivre");
            c = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }
    
    public int createRoom(String name){
        int status = 0;
        try (Connection c = getConnection();
                PreparedStatement prepare = c.prepareStatement(CREATE_ROOM)) {
            
            prepare.setString(1, name);
            status = prepare.executeUpdate();
            
        } catch (Exception e) {
        }
        return status;
    }
    
    public int addRoomConnection(int roomID, int userID){
        int status = 0;
        try(Connection c = getConnection();
                PreparedStatement prepare = c.prepareStatement(ADD_ROOM_CONNECTION)){
            
            prepare.setInt(1,roomID);
            prepare.setInt(2,userID);
            status = prepare.executeUpdate();
            
        } catch (Exception e){
            System.out.println(e);
        }
        return status;
    }
    
    public int deleteRoomConnection(int roomID, int userID){
        int status = 0;
        try(Connection c = getConnection();
                PreparedStatement prepare = c.prepareStatement(DELETE_ROOM_CONNECTION)){
            
            prepare.setInt(1, roomID);
            prepare.setInt(2,userID);
            status = prepare.executeUpdate();
            
        }catch (Exception e){
            System.out.println(e);
        }
        return status;
    }
    
    public int changeRoomName(int roomID, String name){
        int status = 0;
        try(Connection c = getConnection();
                PreparedStatement prepare = c.prepareStatement(UPDATE_ROOM_NAME)){
            
            prepare.setInt(2, roomID);
            prepare.setString(1, name);
            status = prepare.executeUpdate();
            
        }catch(Exception e){
            System.out.println(e);
        }
        return status;
    }
    
    public List<Integer> getRoomMember(int roomID){
        
        List<Integer> list = new ArrayList<>();
        
        try (Connection c = getConnection();
                PreparedStatement prepare = c.prepareStatement(SELECT_ROOM_MEMBER_BY_ROMID)) {
            
            prepare.setInt(1, roomID);
            ResultSet rs = prepare.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("userID"));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
