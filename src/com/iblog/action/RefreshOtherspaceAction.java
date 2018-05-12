package com.iblog.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iblog.DAO.BlogDAO;
import com.iblog.DAO.UserDAO;
import com.iblog.model.Blog;
import com.iblog.model.User;

public class RefreshOtherspaceAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse reponse) {
		// TODO Auto-generated method stub
		String otherID = request.getParameter("otherID");
		String pagenumber = request.getParameter("pagenumber");
		System.out.println(otherID);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		UserDAO uDAO = UserDAO.getInstance();
		User other = uDAO.view(otherID);
		System.out.println(other.getNickname());
		BlogDAO bDAO = BlogDAO.getInstance();
		ArrayList<Blog> blogs = bDAO.view(otherID, "", "", "", pagenumber);
		int followed = uDAO.isFollowed(user.getUserID(), otherID);
		int count = bDAO.countBlog(other.getUserID(), "", "", "");
		int maxPage;
		if(count%10==0){
			maxPage = count/10;
		}else{
			maxPage = count/10+1;
		}
		System.out.println(maxPage);
		request.setAttribute("blogs", blogs);
		request.setAttribute("other", other);
		request.setAttribute("followed", followed);
		request.setAttribute("pagenum", pagenumber);
		request.setAttribute("maxPage", maxPage);
		return "otherinfo.jsp";
	}

}
