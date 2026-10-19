package service;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MemberDAO;

public class IdCheckCommand implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
	      
	      String id = request.getParameter("id");
	      MemberDAO dao = new MemberDAO();
	      boolean isDuplicate = dao.idCheck(id);
	      
	      //json 형태로 직접 응답
	      //JSON은 데이터를 서버와 클라이언트가 주고받기 위해 사용하는 문자열
	      /* JSON 형식의 데이터 "키:값"
	       * {
	       *   "name":"홍길동",
	       *   "age":20,
	       *   "phone":"010"
	       * }
	      */
	      response.setContentType("application/json, charset=utf-8");
	      //응답의 종류를 알려주는 것. 내가 보내는 응답은 JSON, 문자는 UTF-8로 읽어라
	      PrintWriter out = response.getWriter();
	      //서버가 응답할 통로 객체. HTML,JSON 같은 문자열을 클라이언트에게 보내는 도구
	      out.print("{\"isDuplicate\":"+ isDuplicate+"}");
	      //JSON형식의 데이터. 실제로 만들어지는 문자열 {"isDuplicate":true}
	      return false;
	}

}
