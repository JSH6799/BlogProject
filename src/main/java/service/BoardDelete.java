package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BoardDAO;
import model.MemberDTO;

public class BoardDelete implements Command {
	
	private static final String UPLOAD_DIR="C:/upload";

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		
		if(loginMember == null) {
			response.sendRedirect(request.getContextPath()+"/member/login");
			return false;
		}
		int bno = Integer.parseInt(request.getParameter("bno"));
		String imgPath = new BoardDAO().delete(bno, loginMember.getId());
		
		if(imgPath != null) {
			try {
				Path target = Paths.get(UPLOAD_DIR, imgPath);
				Files.deleteIfExists(target);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(request.getContextPath()+"/blog/list");
		return false;
	}

}
