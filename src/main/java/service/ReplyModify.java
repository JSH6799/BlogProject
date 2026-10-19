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

public class ReplyModify implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberDTO dto = (MemberDTO)session.getAttribute("loginMember");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(dto == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			sendJson(response,result);
			return false;
		}
		
		int rno = Integer.parseInt(request.getParameter("rno"));
		String content = request.getParameter("content");
		int updateCount = new ReplyDAO().update(rno, content, dto.getId());
		
		if(updateCount > 0) {
			result.put("success", true);
			result.put("content", content);
		}else {
			result.put("success", false);
			result.put("message", "본인이 작성한 댓글만 수정이 가능합니다.");
		}
		sendJson(response, result);

		return false;
	}
	
	
	private void sendJson(HttpServletResponse response, Map<String, Object> result) {
		
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		String json = gson.toJson(result);
		try {
			PrintWriter out = response.getWriter();
			out.print(json);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
