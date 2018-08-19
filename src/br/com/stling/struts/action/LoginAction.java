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

import br.com.stling.bean.Usuario;
import br.com.stling.dao.UsuarioDao;
import br.com.stling.util.Constantes;
import br.com.stling.util.MessageBundle;

/**
 *
 * @author eswar@vaannila.com
 */
public class LoginAction extends LoginActionImpl {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = new JSONObject();
    	HttpSession session = request.getSession();
    	PrintWriter out = response.getWriter();
		
		String email	= request.getParameter("usuario.email");
        String senha = request.getParameter("usuario.senha");
        
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario usuario;
		try {
			usuario = usuarioDao.findByEmailPassword(new Usuario(email, senha));
			
			if (usuario != null) {
				if(usuario.getTipo().getId().equals(Constantes.TYPE_USER_SALESMAN)){
					session.setAttribute("usuario", usuario);
                	
		            result.put("idResult", Constantes.SUCCESS_LOGIN);
		            result.put("result", MessageBundle.getString("success_login_message"));
				}else{
					result.put("idResult", Constantes.ERROR_UNAUTHORIZED);
		            result.put("result", MessageBundle.getString("error_unauthorized_message"));
				}
	        } else {
	        	result.put("idResult", Constantes.ERROR_USER_OR_PASSWORD_INVALID);
	    		result.put("result", MessageBundle.getString("error_user_or_password_invalid_message"));
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        response.setHeader("Cache-Control","no-cache"); // p/ HTTP 1.1  
        response.setHeader("Pragma","no-cache");        // p/ HTTP 1.0  
        response.setDateHeader ("Expires", 0);          // evita o caching no servidor proxy  
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");	
        
    	out.print(result);	
		
	}
}
