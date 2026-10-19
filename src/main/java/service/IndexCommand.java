package service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BoardDAO;
import model.BoardDTO;

public class IndexCommand implements Command {

	@Override
	public boolean doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		List<BoardDTO> list = new BoardDAO().selectPopular();
		List<BoardDTO> newList = new BoardDAO().selectNew();
		
		request.setAttribute("poplist", list);
		request.setAttribute("newList", newList);
		
		return true;
	}

}
