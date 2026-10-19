package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Command;
import service.IndexCommand;

import java.io.IOException;

@WebServlet("/main/*")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IndexController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pathInfo = request.getPathInfo();
		System.out.println("pathInfo: "+pathInfo);
		
		String page = null;
		Command command = null;
		
		switch(pathInfo) {
		case "/":
		case "/index":
			command = new IndexCommand();
			page="/index.jsp";
			break;
		}
		
		boolean needForward = command.doCommand(request, response);
		
		if(needForward && page != null) {
			request.getRequestDispatcher(page).forward(request, response);
		}

	}
}
