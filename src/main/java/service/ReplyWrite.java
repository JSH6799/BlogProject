package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MemberDTO;
import model.ReplyDAO;
import model.ReplyDTO;

public class ReplyWrite implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(loginMember == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다");
			sendJson(response, result);
			return false;
		}
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String content = request.getParameter("content");
		
		ReplyDTO dto = new ReplyDTO();
		dto.setBno(bno);
		dto.setWriter(loginMember.getId());
		dto.setContent(content);
		
		ReplyDAO dao = new ReplyDAO();
		int rno = dao.insert(dto);
		
		result.put("success", true);
		result.put("rno", rno);
		result.put("writerNickname", loginMember.getNickname());
		result.put("content", content);
		
		sendJson(response, result);
		return false;
	}
	
	
	private void sendJson(HttpServletResponse response, Map<String, Object> result) {
		// Map과 json이 유사({키:값})하기 때문에 gson을 사용하여 map을 json 형식으로 변환하여 사용
		
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		String json = gson.toJson(result);	// result 객체를 json 형식으로 데이터 변환
		try {
			PrintWriter out = response.getWriter();
			out.print(json);	// 호출한 클라이언트로 json 데이터를 보낸다
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
