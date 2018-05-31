package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.DBUtil;
import tool.ImageUtil;
import tool.Mytool;

/**
 * Servlet implementation class GetWaterByAttacked
 */
@WebServlet("/GetWaterByAttacked")
public class GetWaterByAttacked extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetWaterByAttacked() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("get water after attack");
	    Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = request.getParameter("pid");
        System.out.println(id);
        //String attackMode = request.getParameter("attackMode");
        String uid;
        String mode;
        String pid;
        
        try {
            
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM afterwatermark where id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                uid = rs.getString("uid");
                mode = rs.getString("mode");
                pid = rs.getString("pid");
                System.out.println(uid+mode+pid);
                String wmarkPath = WaterMarkIn.OUT(uid, mode, id, pid);
                
                System.out.println(wmarkPath);
                
                response.setContentType("image/png");
                FileInputStream inputStream =ImageUtil.readImage(wmarkPath);
                int length = inputStream.available();
                byte  data[] = new byte[length];
                inputStream.read(data);
                OutputStream toClient = response.getOutputStream();
                toClient.write(data);
                toClient.flush();
                toClient.close(); 
                inputStream.close();
            }
            System.out.println(" in end ");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, ps, rs);

        }

    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
