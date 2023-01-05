package com.groupeisi.registration.entities;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.mysql.cj.xdevapi.PreparableStatement;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet ("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection cnx =null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cnx = DriverManager.getConnection("jdbc:mysql://localhost:/company","root","");
			PreparedStatement pstm = cnx.prepareStatement("insert into users (uname,upwd,uemail,umobile) values (?,?,?,?)");
			pstm.setString(1, uname);
			pstm.setString(2, upwd);
			pstm.setString(3, uemail);
			pstm.setString(4, umobile);
			
			int rowCount = pstm.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (rowCount > 0) {
				
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				cnx.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
