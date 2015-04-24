package com.example.assignment01;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AnagramServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
	{
		resp.setContentType("text/html");
		
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login = us.createLoginURL("/");
		String logout = us.createLogoutURL("/");
		String wordSearch = req.getParameter("wordSearch");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		WordList search = null;
		
		if(wordSearch.length() > 0)
		{
			Character[] chars = new Character[wordSearch.length()];
			for (int i = 0; i < chars.length; i++)
			    chars[i] = wordSearch.charAt(i);
			
			Arrays.sort(chars, new Comparator<Character>() {
			    public int compare(Character c1, Character c2) {
			        int cmp = Character.compare(
			            Character.toLowerCase(c1.charValue()),
			            Character.toLowerCase(c2.charValue())
			        );
			        if (cmp != 0) return cmp;
			        return Character.compare(c1.charValue(), c2.charValue());
			    }
			});
			
			StringBuilder sb = new StringBuilder(chars.length);
			for (char c : chars) sb.append(c);
			String wordSearchR = sb.toString();
			
			Key search_key = KeyFactory.createKey("WordList", wordSearchR);
			pm = PMF.get().getPersistenceManager();
			try
			{
				search = pm.getObjectById(WordList.class, search_key);
			} 
			catch(Exception e)
			{
				
			}
			pm.close();
		}
		else
		{ 
			resp.sendRedirect("/");
		}
		
		String result = "<strong>Result:</strong> <br/>";
		if(search != null)
		{
			if(search.wordCount() > 0)
			{
				for(int i = 0; i < search.wordCount(); i++){
					if(!search.getWord(i).equals(wordSearch))
						result = result + search.getWord(i) + "<br/>";	
				}
			}
		}
		else
			result = result + "<strong>No words with this key</strong>";
		
		
		req.setAttribute("user", u);
		req.setAttribute("login", login);
		req.setAttribute("logout", logout);
		req.setAttribute("search_list", result);
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
	    rd.forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		resp.setContentType("text/html");
		
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login = us.createLoginURL("/");
		String logout = us.createLogoutURL("/");
		String wordAdd = req.getParameter("wordAdd");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		WordList add = null;
		
		if(wordAdd.length() > 0)
		{
			Character[] chars = new Character[wordAdd.length()];
			for (int i = 0; i < chars.length; i++)
			    chars[i] = wordAdd.charAt(i);
			
			Arrays.sort(chars, new Comparator<Character>() {
			    public int compare(Character c1, Character c2) {
			        int cmp = Character.compare(
			            Character.toLowerCase(c1.charValue()),
			            Character.toLowerCase(c2.charValue())
			        );
			        if (cmp != 0) return cmp;
			        return Character.compare(c1.charValue(), c2.charValue());
			    }
			});
			
			StringBuilder sb = new StringBuilder(chars.length);
			for (char c : chars) sb.append(c);
			String wordAddR = sb.toString();
			
			Key check = KeyFactory.createKey("WordList", wordAddR);
			pm = PMF.get().getPersistenceManager();
			try
			{
				add = pm.getObjectById(WordList.class, check);
				add.addWord(wordAdd);
				pm.makePersistent(add);
			}
			catch(Exception e)
			{
				add = new WordList();
				add.setID(check);
				add.addWord(wordAdd);
				pm.makePersistent(add);
			}
			pm.close();
		}
		else
		{ 
			resp.sendRedirect("/");
		}
		
		String search = "<strong>Result:</strong> <br/>";
		if(add.wordCount() > 0)
		{
			for(int i = 0; i < add.wordCount(); i++){
				if(!add.getWord(i).equals(wordAdd))
					search = search + add.getWord(i) + "<br/>";	
			}
		}
		else
			search = search + "<strong>Word has been added</strong>";
		
		req.setAttribute("user", u);
		req.setAttribute("login", login);
		req.setAttribute("logout", logout);
		req.setAttribute("search", search);
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
	    rd.forward(req, resp);
	}
}
