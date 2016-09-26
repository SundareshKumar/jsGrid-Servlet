package com.zetcode.util;

import com.google.common.base.Splitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.apache.derby.jdbc.ClientDataSource;

public class Utils {

    public static DataSource getDataSource() {

        ClientDataSource ds = new ClientDataSource();
        
        ds.setDatabaseName("testdb");
        ds.setUser("app");
        ds.setPassword("app");
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        
        return ds;
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {

        BufferedReader br = null;
        Map<String, String> dataMap = null;

        try {

            InputStreamReader reader = new InputStreamReader(
                    request.getInputStream());
            br = new BufferedReader(reader);

            String data = br.readLine();

            dataMap = Splitter.on('&')
                    .trimResults()
                    .withKeyValueSeparator(
                            Splitter.on('=')
                            .limit(2)
                            .trimResults())
                    .split(data);

            return dataMap;
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        }

        return dataMap;
    }
}
