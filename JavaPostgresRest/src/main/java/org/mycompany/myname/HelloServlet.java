package org.mycompany.myname;

/**
 * Created by anon on 1/10/2017.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.mycompany.hiber.TestEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        String result = "nodata";
        try {
            result = testjava();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            result = ex.getMessage();
        }
        result = "Hello from servlet postgres " + result;
        httpServletResponse.getWriter().print(result);
    }

    private String testHiber() {
        SessionFactory sf = GetF();
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from TestEntity").list();
        String s = "";
        for (TestEntity t : (List<TestEntity>) result) {
            s += "Event (" + t.getName() + ") : " + t.getId();
        }
        session.getTransaction().commit();
        session.close();
        return s;
    }

    SessionFactory GetF() {
        Configuration configuration = new Configuration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    private String testjava() throws SQLException {
        Connection conn = null;
        String format = String.format("jdbc:postgresql://localhost/%s", "postgres");
        Properties props = new Properties();
        props.put("user", "postgres");
        props.put("password", "postgres");
        try {
            conn = DriverManager.getConnection(format, props);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String r = "clear";

        ResultSet res = conn.prepareStatement("select \"Name\" from \"Test\"").executeQuery();
        while (res.next()) {
            r = res.getString(1);
        }
        return r;
    }
}