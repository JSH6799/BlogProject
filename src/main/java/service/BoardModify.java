package service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.BoardDAO;
import model.BoardDTO;
import model.MemberDTO;

public class BoardModify implements Command {
	
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
		
		BoardDAO dao = new BoardDAO();
		BoardDTO board = dao.selectOne(bno);	// 수정폼에 채울 기존 데이터
		
		if(board == null) {
			response.sendRedirect(request.getContextPath()+"/blog/list");
			return false;
		}
		if(!board.getWriter().equals(loginMember.getId())) {
			request.setAttribute("board", board);
			request.setAttribute("errMsg", "본인이 작성한 글만 수정 가능합니다");
			return true;
		}
		// get 방식으로 요청이 들어오면 수정폼에 기존 값을 채워서 보여주기
		if("GET".equalsIgnoreCase(request.getMethod())) {
			request.setAttribute("board", board);
			return true;
		}
		
		// post 방식으로 요청이 들어오면 실제 수정 처리
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String removeImg = request.getParameter("removeImg");	// true -> 이미지 제거 요청
		
		String oldImgPath = board.getImgPath();
		String finalImgPath = oldImgPath;	// 기본값 : 기본 이미지 유지
		
		Part filePart = request.getPart("imgFile");
		if(filePart != null && filePart.getSize() > 0) {
			// 새 이미지를 올린 경우 -> 기존 이미지 지우고 새 이미지로 교체
			deleteOldFile(oldImgPath);	// 예전 이미지 삭제
			finalImgPath = saveFile(filePart);
		}else if("true".equals(removeImg)){
			// 새 이미지 없이 이미지 제거만 누른 경우 -> 기존 이미지만 지우고 null
			deleteOldFile(oldImgPath);
			finalImgPath = null;
		}
		
		// 둘 다 아니면 finalImgPath는 oldImgPath 그대로 변경 없음
		BoardDTO updateDto = new BoardDTO();
		
		updateDto.setBno(bno);
		updateDto.setTitle(title);
		updateDto.setContent(content);
		updateDto.setWriter(loginMember.getId());
		updateDto.setImgPath(finalImgPath);
		
		dao.update(updateDto);
		response.sendRedirect(request.getContextPath()+"/blog/list");
		return false;
	}

	
	public String saveFile(Part filePart) throws IOException {
		String oriFileName = filePart.getSubmittedFileName();
		String saveFileName = System.currentTimeMillis()+"-"+oriFileName;
		Path uploadDir = Paths.get(UPLOAD_DIR);
		if(!Files.exists(uploadDir)) {
			Files.createDirectories(uploadDir);
		}
		Path targetPath = uploadDir.resolve(saveFileName);
		try {
			InputStream input = filePart.getInputStream();
			Files.copy(input, targetPath);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return saveFileName;
	}
	
	
	private void deleteOldFile(String fileName) {
		if(fileName == null) return;
		try {
			Files.deleteIfExists(Paths.get(UPLOAD_DIR, fileName));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
