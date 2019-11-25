package server.integration;

import common.Credentials;
import common.Exceptions.AccessDeniedException;
import common.Exceptions.UserNameIsTakenException;
import common.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DBH {
    private final String SELECT_FROM_FILES = "SELECT * FROM Files WHERE name = ?";
    private final String SELECT_FROM_USERS = "SELECT * FROM Users WHERE username = ? AND password = ?";
    private final String INSERT_TO_FILES = "INSERT INTO Files (name, owner, fileSize,permission) VALUES (?,?,?,?)";
    private final String SELECT_ALL_FILES = "SELECT * FROM FILES";
    private final String DELETE_FILE =  "DELETE FROM Files WHERE name = ?";
    private final String SELECT_FILE =  "SELECT * FROM Files WHERE name = ?";
    private final String SELECT_USER = "SELECT * FROM Users WHERE username = ?";
    private final String INSERT_NEW_USER =  "INSERT INTO Users (username, password) VALUES (?,?)";

    public DBH(){

    }

    public boolean signUp(Credentials cred) throws UserNameIsTakenException {
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER);
            preparedStatement.setString(1,cred.getUserName());
            ResultSet result = preparedStatement.executeQuery();
            if(!result.next()){
                preparedStatement = conn.prepareStatement(INSERT_NEW_USER);
                preparedStatement.setString(1,cred.getUserName());
                preparedStatement.setString(2,cred.getPassword());
                return preparedStatement.executeUpdate() > 0;
            } else {
                throw new UserNameIsTakenException(cred.getUserName()+" Already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFile(File file){
        if(fileNameExists(file.getName())){
            return false;
        }
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_TO_FILES);
            preparedStatement.setString(1,file.getName());
            preparedStatement.setString(2,file.getOwner());
            preparedStatement.setString(3,String.valueOf(file.getSize()));
            preparedStatement.setString(4,file.getPermission());
            return !preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean fileNameExists(String fileName){
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FROM_FILES);
            preparedStatement.setString(1,fileName);
            ResultSet result =  preparedStatement.executeQuery();
            if(result.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public File downloadFile(String fileName) throws NoSuchElementException{
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FROM_FILES);
            preparedStatement.setString(1,fileName);
            ResultSet result =  preparedStatement.executeQuery();
            if(result.next()){
                return new File(
                        result.getString("name"),
                        result.getString("owner"),
                        Integer.parseInt(result.getString("fileSize")),
                        checkPermission(result.getString("permission"))
                        );
            }else{
                throw new NoSuchElementException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File deleteFile(String fileName, Credentials cred) throws AccessDeniedException, NoSuchElementException {
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FILE);
            preparedStatement.setString(1,fileName);
            ResultSet result =  preparedStatement.executeQuery();
            if(result.next()){
                if(result.getString("permission").equals("rw")||result.getString("owner").equals(cred.getUserName())){
                    File removedFile = new File(
                            result.getString("name"),
                            result.getString("owner"),
                            Integer.parseInt(result.getString("fileSize")),
                            checkPermission(result.getString("permission"))
                    );
                    preparedStatement = conn.prepareStatement(DELETE_FILE);
                    preparedStatement.setString(1,fileName);
                    preparedStatement.executeUpdate();
                    return removedFile;
                } else {
                    throw new AccessDeniedException();
                }
            } else{
                throw new NoSuchElementException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkPermission(String permission){
        if(permission.equals("r")){
            return true;
        }
        return false;
    }
    public LinkedList<File> getFiles(){
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_FILES);
            ResultSet result =  preparedStatement.executeQuery();
            LinkedList queue = new LinkedList();
            while(result.next()){
                queue.add(new File(
                        result.getString("name"),
                        result.getString("owner"),
                        Integer.parseInt(result.getString("fileSize")),
                        checkPermission(result.getString("permission"))
                ));
            }
            return queue;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean authorizeUser(Credentials cred){
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FROM_USERS);
            preparedStatement.setString(1,cred.getUserName());
            preparedStatement.setString(2,cred.getPassword());
            ResultSet result =  preparedStatement.executeQuery();
            if(result.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

