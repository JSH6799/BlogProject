package service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.BoardDAO;
import model.BoardDTO;
import model.MemberDTO;
import model.ReplyDAO;
import model.ReplyDTO;

public class MypageCommand implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		// 세션 객체 생성
		HttpSession session = request.getSession();
		// loginMember 속성을 MemberDTO 개체로 형변환
		MemberDTO dto = (MemberDTO)session.getAttribute("loginMember");
		
		// loginMember가 null과 같으면 로그인 페이지로 이동 시킨다
		if(dto == null) {
			response.sendRedirect(request.getContextPath()+"/member/login");
			return false;
		}
		
		// loginMember에 있는 id를 변수에 저장하고
		String id = dto.getId();
		
		// ReplyDAO, BoardDAO 객체를 생성하고 메서드를 호출하면서 id를 보낸다
		List<BoardDTO> myPosts = new BoardDAO().selectListByWriter(id);
		List<BoardDTO> myLikes = new BoardDAO().selectLikedList(id);
		List<ReplyDTO> myReplies = new ReplyDAO().selectListByWriter(id);
		
		// 메서드를 처리하고 리턴 된 가변배열의 값 3개를 속성에 담는다
		request.setAttribute("myPosts", myPosts);
		request.setAttribute("myReplies", myReplies);
		request.setAttribute("myLikes", myLikes);
		
		return true;
	}
	
	
}
