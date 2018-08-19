/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.stling.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import br.com.stling.util.MessageBundle;

public class RegisterAction extends RegisterActionImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterAction() {
		super();
	}

	/**
	 * This is the action called from the Struts framework.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param form
	 *            The optional ActionForm bean for this request.
	 * @param request
	 *            The HTTP Request we are processing.
	 * @param response
	 *            The HTTP Response we are processing.
	 * @throws java.lang.Exception
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = null;
		PrintWriter out = response.getWriter();
//		HttpSession session = request.getSession();
		String json = null;
		try {
			String method = request.getParameter("method");
			
			switch (method) {
			case "saveCliente":
				result = saveCliente(request);
				break;
			default:
				result = new JSONObject();
				result.put("result", MessageBundle.getString("method_not_found"));
				break;
			}

			response.encodeRedirectURL("ISO-8859-1");
			response.encodeURL("ISO-8859-1");
			response.setContentType("application/json;charset=iso-8859-1");
			response.setHeader("Cache-Control", "no-store");

		} catch (Exception e) {
			e.printStackTrace();
			response.encodeRedirectURL("ISO-8859-1");
			response.encodeURL("ISO-8859-1");
			response.setContentType("application/json;charset=iso-8859-1");
			response.setHeader("Cache-Control", "no-store");
			result = new JSONObject();
			result.put("result", MessageBundle.getString("error_panel_register_message"));
		}

		out.print(result == null ? json : result);

	}

	
}
