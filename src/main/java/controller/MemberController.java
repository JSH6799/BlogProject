package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Command;
import service.IdCheckCommand;
import service.LoginCommand;
import service.LogoutCommand;
import service.MemberInsert;
import service.MypageCommand;

import java.io.IOException;

@WebServlet("/member/*")
// URL을 통해서 요청하는 주소가 /member로 시작하면 모든 요청(*)은 이 클래스가 자동으로 실행
// jsp 파일을 직접 실행하는 것이 아닌 servlet을 통해 실행
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;	// <- 변수
	// 직렬화 관련 버전 번호 -> 이클립스에서 자동으로 생성
	
    public MemberController() {
    	// 생성자
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// doGet() : get 방식으로 넘어온 데이터를 처리하는 메서드
//		// 전송하는 방식에 따라 doGet() 또는 doPost() 메서드가 자동으로 실행됨
//		// request : 사용자가 보내온 데이터를 요청할 때 사용. 요청하는 모든 값을 저장
//		// response : 사용자가 처리한 데이터를 응답할 때 사용. 응답하는 모든 값을 저장
//		RequestDispatcher rd = request.getRequestDispatcher("/member/login.jsp");	// 동작 명령어
//		rd.forward(request, response);
//		// forward(request, response) : 요청, 응답한 모든 값을 "/member/login.jsp"로 보내라
		
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");
//		
//		String id = request.getParameter("id");
//		String pwd = request.getParameter("pwd");
//		System.out.println("id: "+id);
//		System.out.println("pwd : "+pwd);
		
		doAction(request,response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pathInfo = request.getPathInfo();
		// localhost:8088/blogProject/member/join
		// 요청된 url이 위와 같을 때 가장 마지막에 불러오는 join의 데이터를 pathInfo에 저장
		System.out.println("pathInfo: "+pathInfo);

		String page = null;
		Command command = null;
		
		switch(pathInfo) {
		case "/join":
			//localhost:8090/blogProject/member/join
			command = new MemberInsert();	// 다형성 활용
			page="/mem/join.jsp";
			break;
		case "/idCheck":
			//localhost:8090/blogProject/member/idCheck
			command = new IdCheckCommand();
			page = null;
			break;
		case "/login":
			//localhost:8090/blogProject/member/login
			command = new LoginCommand();
			page = "/mem/login.jsp";
			break;
		case "/logout":
			command = new LogoutCommand();
			page = null;
			break;
		case "/mypage":
			command = new MypageCommand();
			page = "/mem/mypage.jsp";
			break;
		}
		
		boolean needForward = command.doCommand(request, response);
		
		if(needForward && page != null) {
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}

}
