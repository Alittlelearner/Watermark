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
import tool.ImageUtil;
import tool.Mytool;

/**
 * Servlet implementation class MakeUNWaterMark
 */
@WebServlet("/MakeUNWaterMark")
public class MakeUNWaterMark extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeUNWaterMark() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = request.getParameter("pid");
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
                String wmarkPath = Mytool.getSrcPath(WaterMarkIn.OUT(uid, mode, id, pid));
                System.out.println(wmarkPath);
                // ImageUtil.readImage(waterMarkedPath);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(wmarkPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, ps, rs);

        }

    }

}
