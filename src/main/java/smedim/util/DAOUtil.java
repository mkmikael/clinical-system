/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smedim.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mikael Lima
 */
public class DAOUtil implements Serializable {
    
    public static Connection conexao() {
        try {
            String url = "jdbc:mysql://localhost:3306/smedim";
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
