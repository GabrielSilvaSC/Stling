/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.stling.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author eswar@vaannila.com
 */
public abstract class LoginActionImpl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	abstract protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	
}
