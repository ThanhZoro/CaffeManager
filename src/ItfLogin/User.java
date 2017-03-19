/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItfLogin;

/**
 *
 * @author Zoro
 */
public class User {
    private String userName;
    private String pass;
    private int role;

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

    public User(String userName, int role) {
        this.userName = userName;
        this.role = role;
    }

    public User(String userName, String pass, int role) {
        this.userName = userName;
        this.pass = pass;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", pass=" + pass + ", role=" + role + '}';
    }
    
    
    
}
