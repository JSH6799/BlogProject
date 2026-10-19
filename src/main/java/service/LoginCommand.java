package service;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MemberDAO;
import model.MemberDTO;

public class LoginCommand implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if("get".equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.login(id);
		
		if(dto != null && BCrypt.checkpw(pwd, dto.getPwd())) {
			// BCrypt.checkpw : 평문 비밀번호와 암호화 된 비밀번호의 일치 여부 확인
			// 쿠키 : 로그인 정보를 내 컴퓨터에 저장 -> ex) 로그인 화면에서 아이디/비밀번호 저장. 웹사이트 광고창(오늘 하루 보지 않기)
			// 세션 : 로그인 정보를 서버에 저장
			HttpSession session = request.getSession();	// 세션 만들기
			session.setAttribute("loginMember", dto);
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}else {
			request.setAttribute("loginFail", "아이디 또는 비밀번호를 확인하세요");
			// 처리된 값을 JSP로 출력을 하려면 request 속성 또는 session 속성에 저장해서 포워딩한다
			// request.setAttribute : request 속성에 뒤에 오는 내용을 저장해서 JSP로 보내라
			// session 속성 : 계속 유지됨 -> ex) 로그인을 한 후 로그아웃을 하기 전까지 화면 전환이 이뤄져도 로그인이 유지됨
			// request 속성 : 현재 페이지에서만 해당 값이 유효 -> 다른 페이지로 넘어가면 없어짐
			return true;
		}
		
	}

}
