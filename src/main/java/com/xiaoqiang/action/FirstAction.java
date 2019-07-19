package com.xiaoqiang.action;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import com.xiaoqiang.utils.CertificateUtil;



/**
 * Servlet implementation class FirstAction
 */
@WebServlet("/FirstAction")
public class FirstAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		    CertificateUtil certificateUtil=new CertificateUtil();
		    String insuredBean=certificateUtil.doPost("0042", "·¶Ï¼", "123.txt");
		    request.setCharacterEncoding("UTF-8");
		    request.setAttribute("xiaoqiang", insuredBean);
		    request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	 

}
