package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.DBUtil;

/**
 * Servlet implementation class GetValueFromDatabase
 */
@WebServlet("/GetValueFromDatabase")
public class GetValueFromDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetValueFromDatabase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = request.getParameter("id");
        String table = request.getParameter("table"); 
        String value = request.getParameter("value");
        
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT "+ value +" FROM  "  + table  + " where id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {                               
                String gotValue = rs.getString(value);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(gotValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, ps, rs);
            
        }
	}
}
