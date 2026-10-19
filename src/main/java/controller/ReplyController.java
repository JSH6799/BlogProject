package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Command;
import service.ReplyDelete;
import service.ReplyModify;
import service.ReplyWrite;

import java.io.IOException;

@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyController() {
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
		
		Command command = null;
		
		switch(pathInfo) {
		case "/write":
			command = new ReplyWrite();
			break;
		case "/delete":
			command = new ReplyDelete();
			break;
		case "/modify":
			command = new ReplyModify();
			break;
		}
		
		command.doCommand(request, response);
		// 모든 reply 기능은 Ajax를 사용할 것이기 때문에 forward 할 page가 없다
		
		// 댓글 기능은 포워딩 필요 없음 -> service에서 모두 return false가 반환됨
		// -> view 페이지 안에서 비동기식으로 데이터를 주고 받기 때문에
		// 포워딩은 jsp를 실행하기 위한 것
		
	}

}
