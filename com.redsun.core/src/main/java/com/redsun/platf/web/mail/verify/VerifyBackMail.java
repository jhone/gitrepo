package com.redsun.platf.web.mail.verify;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-30</br>
 * Time: 下午4:25</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-30    joker pan    created
 * <pre/>
 */
public class VerifyBackMail extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        ConDB con;
        String stu_name;
        String msg;
        HttpSession session = request.getSession();

        String stu_nameMd5 = request.getParameter("stu_nameMd5");
        String randMd5 = request.getParameter("randMd5");

        try {
            con = new ConDB();
            stu_name = con.getVerify(stu_nameMd5, randMd5);
            if (stu_name != null) {
                msg = "注册成功,请返回登录页面";
//                con.addStudent(stu_name);
//                con.delStudentTemp(stu_name);
//                con.delVerify(stu_name);
//
            } else {
                msg = "错误";
            }
//            con.close();
            session.setAttribute("msg", msg);
            response.sendRedirect("result.jsp");

        } catch (ClassNotFoundException e) {
            throw new ServletException(e.fillInStackTrace());
        } catch (SQLException e) {
            throw new ServletException(e.fillInStackTrace());
        }

    }
     ///do verify///////////////////////////////////////////////////////////
    private Connection con;
    private Statement sta;
    private ResultSet res;
    private PreparedStatement pres;

    public void delVerify(String stu_name)
            throws SQLException {
        String sql = "delete from verify where stu_name='" + stu_name + "'";
        sta.executeUpdate(sql);
    }

    public String getVerify(String stu_nameMd5, String randMd5)
            throws SQLException {
        String stu_name = null;
        String sql = "select stu_name from verify where stu_nameMd5=? and randMd5=?";
        pres = con.prepareStatement(sql);

        pres.setString(1, stu_nameMd5);
        pres.setString(2, randMd5);
        res = pres.executeQuery();
        while (res.next()) {
            stu_name = res.getString("stu_name");
        }
        return stu_name;
    }

    public void addStudent(String stu_name)
            throws SQLException {
        String sql = "insert into student select * from studentTemp where stu_name='" + stu_name + "'";
        sta.executeUpdate(sql);
    }

    public String getStu_name(String stu_email)
            throws SQLException {

        String sql = "select stu_name from student where stu_email='" + stu_email + "'";
        String stu_name = null;

        res = sta.executeQuery(sql);
        while (res.next()) {
            stu_name = res.getString("stu_name");
        }

        return stu_name;
    }

    public void resetPassword(String stu_name, String stu_password)
            throws SQLException {
        String sql = "update student set stu_password=? where stu_name=?";
        pres = con.prepareStatement(sql);
        pres.setString(1, stu_password);
        pres.setString(2, stu_name);
        pres.executeUpdate();
    }

    public boolean existStudent(String stu_name, String stu_password)
            throws SQLException {
        boolean exist = false;
        String sql = "select * from student where stu_name=? and stu_password=?";
        pres = con.prepareStatement(sql);
        pres.setString(1, stu_name);
        pres.setString(2, stu_password);
        res = pres.executeQuery();
        if (res.next()) {
            exist = true;
        }
        return exist;
    }

}