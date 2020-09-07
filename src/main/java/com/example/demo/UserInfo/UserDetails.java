package com.example.demo.UserInfo;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

public class UserDetails implements UserDetailsService {
    private  String URL = "jdbc:postgresql://192.168.1.24:5432/postgres";
    private  String User = "postgres";
    private String pass = "wtfmen";
    Connection con ;

    public UserDetails() throws SQLException {
     //   con = DriverManager.getConnection(URL, User, pass);
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = this.findUserbyUername(username);
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            builder.roles(user.getRoles());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    private User findUserbyUername(String username) {
        if (username.equalsIgnoreCase("admin")) {
            return new User(username, "admin123", "ADMIN");
        }

        if (username.equalsIgnoreCase("user")) {
            return new User(username, "password1234", "USER");
        }
        return null;
    }

    private User findUserbyUername(String username,String z) {
        User user = null;
        try {
            Statement st = con.createStatement();
            String dbRequest = "SELECT name, password, role FROM ACCAUNT WHERE name=name";
            ResultSet set = st.executeQuery(dbRequest);

            while (set.next()) {
                if(username.equals(set.getString("name"))) user=new User(username,set.getString("password"),set.getString("role").toUpperCase());
            }

            st.close();
            if (username.equalsIgnoreCase("admin")) {
                user= new User(username, "admin123", "ADMIN");
            }

            if (username.equalsIgnoreCase("user")) {
                user= new User(username, "password1234", "USER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
