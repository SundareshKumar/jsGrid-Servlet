package com.zetcode.service;

import com.zetcode.util.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.derby.optional.api.SimpleJsonUtils;
import org.json.simple.JSONArray;

// http://stackoverflow.com/questions/855835/servlet-parameters-and-doput
public class CarService {

    private static final Logger LOG = Logger.getLogger(CarService.class.getName());
    private static JSONArray jarray;

    public static void updateCar(String name, int price) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            DataSource ds = Utils.getDataSource();
            con = ds.getConnection();
            pst = con.prepareStatement("UPDATE CARS SET NAME=?, PRICE=? WHERE NAME=?");
            pst.setString(1, name);
            pst.setInt(2, price);
            pst.setString(3, name);
            pst.executeUpdate();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CarService.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static void deleteCar(String name) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            DataSource ds = Utils.getDataSource();
            con = ds.getConnection();
            pst = con.prepareStatement("DELETE FROM CARS WHERE Name=?");
            pst.setString(1, name);
            pst.executeUpdate();

        } catch (SQLException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static void insertCar(String name, int price) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            DataSource ds = Utils.getDataSource();
            con = ds.getConnection();
            pst = con.prepareStatement("INSERT INTO CARS(NAME, PRICE) "
                    + "VALUES(?, ?)");
            pst.setString(1, name);
            pst.setInt(2, price);
            pst.executeUpdate();

        } catch (SQLException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                
                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static JSONArray getCarsJSON() {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {

            DataSource ds = Utils.getDataSource();
            con = ds.getConnection();
            pst = con.prepareStatement("SELECT NAME, PRICE FROM Cars");
            rs = pst.executeQuery();

            jarray = SimpleJsonUtils.toJSON(rs);

        } catch (SQLException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                
                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return jarray;
    }
}
