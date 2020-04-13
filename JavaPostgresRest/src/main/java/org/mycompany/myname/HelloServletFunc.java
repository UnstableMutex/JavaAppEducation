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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HelloServletFunc extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        List<TestEntity> result=null;
        try {
       result = testjava();
          }  catch (SQLException ex) {
            System.out.println(ex.getMessage());

         }
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

    private List<TestEntity> testjava() throws SQLException {
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
        List<TestEntity> r =  new ArrayList<>();


        CallableStatement cs = conn.prepareCall("select * from public.testtest2(?)");
        cs.setInt(1,1);
        ResultSet res=cs.executeQuery();
        while (res.next()) {
            TestEntity e=new TestEntity();
           e.setId(res.getInt(1));
          e.setName(res.getString(2));
          r.add(e);
        }
        return r;
    }
}