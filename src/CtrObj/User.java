/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CtrObj;

/**
 *
 * @author Zoro
 */
public class User {
    private int idUser;
    private String userName;
    private String pass;
    private int role;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public User() {
    }

    public User(String userName, String pass, int role) {
        this.userName = userName;
        this.pass = pass;
        this.role = role;
    }

    public User(int idUser, String userName, String pass, int role) {
        this.idUser = idUser;
        this.userName = userName;
        this.pass = pass;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", userName=" + userName + ", pass=" + pass + ", role=" + role + '}';
    }
    
    
    
    
    
}
