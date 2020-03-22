package org.mycompany.myname;

/**
 * Created by anon on 1/10/2017.
 */

import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.mycompany.hiber.TestEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        // String result = "nodata";
        //try {
        List<TestEntity> result = testHiber();
      //  }  catch (SQLException ex) {
        //    System.out.println(ex.getMessage());
         //   result = ex.getMessage();
       // }
        String json = new Gson().toJson(result);
        // result = "Hello from servlet postgres " + result;
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().print(json);
    }

    private List<TestEntity> testHiber() {
        SessionFactory sf = GetF();
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from TestEntity").list();
//        String s = "";
//        for (TestEntity t : (List<TestEntity>) result) {
//            s += "Event (" + t.getName() + ") : " + t.getId();
//        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    SessionFactory GetF()
    {
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