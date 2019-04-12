package com.zkyong.demo.util.xstream;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.zkyong.demo.factory.XStreamDateConverter;
@XStreamAlias("Request")
public class User {
    @XStreamAlias("Username")
    private String username;
    @XStreamAlias("Password")
    private String password;
    @XStreamAlias("Regtime")
    @XStreamConverter(value=XStreamDateConverter.class)
    private Date regtime;
    @XStreamAlias("Annexes")
    private Url annexes;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getRegtime() {
        return regtime;
    }
    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }
    public Url getAnnexes() {
        return annexes;
    }
    public void setAnnexes(Url url) {
        this.annexes = url;
    }
    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", regtime=" + regtime + ", annexes=" + annexes
                + "]";
    }
}
