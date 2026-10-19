package service;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MemberDAO;
import model.MemberDTO;

public class MemberInsert implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// get 방식으로 요청이 들어오면 다음 코딩을 실행 안 하게 한다
		if("get".equalsIgnoreCase(request.getMethod())) {
			// request.getMethod() : 요청 방식을 알고 싶을 때
			// equalsIgnoreCase : 대소문자 관계없이 같을 때
			return true;
		}
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
		// 비밀번호 암호화
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		
		MemberDTO dto = new MemberDTO();
		
		dto.setId(id);
		dto.setPwd(hashedPwd);
		dto.setNickname(nickname);
		dto.setEmail(email);
		
		MemberDAO dao = new MemberDAO();
		dao.memberInsert(dto);
		
		response.sendRedirect(request.getContextPath()+"/member/login");
		
		return false;
		// sendRedirect는 무조건 return false
		// sendRedirect가 없으면 = forward가 필요하면 return true
	}

}
