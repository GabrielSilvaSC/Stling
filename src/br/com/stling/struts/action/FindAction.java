/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.stling.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.stling.bean.Usuario;
import br.com.stling.stream.adapter.HibernateProxyTypeAdapter;
import br.com.stling.util.MessageBundle;


/**
 * 
 * @author eswar@vaannila.com
 */
public class FindAction extends FindActionImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 JSONObject result = new JSONObject();
		// JSONArray array = new JSONArray();
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String json = "";
		try {
			String method = request.getParameter("method");

			Usuario usuario = (Usuario) session.getAttribute("usuario");
			GsonBuilder b = new GsonBuilder();
			b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
			Gson gson = b.create();
			if  (usuario != null) {
				
				switch (method) {
				case "findProdutoById":
					json = findProdutoById(request);
					break;
				case "findProdutoByDescricao":
					json = findProdutoByDescricao(request);
					break;
				case "findClienteById":
					json = findClienteById(request);
					break;
				case "findClienteByNome":
					json = findClienteByNome(request);
					break;
				default:
					result = new JSONObject();
					result.put("result", MessageBundle.getString("method_not_found") + " : " + method);
					json = gson.toJson(result);
					break;
				}
			} else {
				result = new JSONObject();
				result.put("result", MessageBundle.getString("error_user_not_found") + " : " + method);
				json = gson.toJson(result);
			}
			
			
			
			
			response.setHeader("Cache-Control", "no-cache"); // p/ HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // p/ HTTP 1.0
			response.setDateHeader("Expires", 0); // evita o caching no servidor
													// proxy
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-store");
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.print(json);
	}

	

	

	
}
