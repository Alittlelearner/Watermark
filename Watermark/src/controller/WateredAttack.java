package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.ImageUtil;
import tool.WaterMakUtil;

/**
 * Servlet implementation class WateredAttack
 */
@WebServlet("/WateredAttack")
public class WateredAttack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WateredAttack() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    /*request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");*/
        String id = request.getParameter("wpid");
        String mode = request.getParameter("mode");
        String wayforattack = "afterwatermark";
        Map<Object, Object> wateredsultMap = ImageUtil.readDBImage(id, "afterwatermark");
        String picpath = (String) wateredsultMap.get("picpath");
        String pathforattact = ImageUtil.TEMP_PATH + File.separator + wayforattack + File.separator;
        WaterMakUtil waterMakUtil = new WaterMakUtil(wayforattack, null, null, pathforattact, 0, 0);
        String attackedPath = waterMakUtil.UseWaterAttack(mode, picpath);
        File file = new File(attackedPath);
        File file2 = new File(pathforattact + "attacked.png");
        if(file2.exists()) {
            file2.delete();
        }
        Files.copy(file.toPath(), file2.toPath());
        FileInputStream inputStream =ImageUtil.readImage(attackedPath);
        response.setHeader("Content-Type","image/png");
        int length = inputStream.available();
        byte  data[] = new byte[length];
        inputStream.read(data);
        OutputStream toClient = response.getOutputStream();
        toClient.write(data);
        toClient.flush();
        toClient.close(); 
        inputStream.close();
	}

	
}
