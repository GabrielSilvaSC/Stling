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

import org.json.simple.JSONObject;

import br.com.stling.bean.Cliente;
import br.com.stling.dao.ClienteDao;
import br.com.stling.util.Constantes;
import br.com.stling.util.MessageBundle;

@SuppressWarnings("unused")
public abstract class RegisterActionImpl extends HttpServlet {
	
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterActionImpl() {
        super();
    }
    
    @SuppressWarnings("unchecked")
	protected JSONObject saveCliente(HttpServletRequest request) throws NumberFormatException, Exception {
		JSONObject result = new JSONObject();
		
		Integer idCliente 	= request.getParameter("cliente.idCliente") != null &&  !request.getParameter("cliente.idCliente").equals("") ? Integer.parseInt(request.getParameter("cliente.idCliente")) : null;
		String nome 	= request.getParameter("cliente.nome");
		String cpfCnpj	= request.getParameter("cliente.cpfCnpj");
		String endereco	= request.getParameter("cliente.endereco");
        
        ClienteDao clienteDao = new ClienteDao();
        Cliente checkCliente = clienteDao.existsCpfCnpJ(cpfCnpj);
        
        if(checkCliente != null) {
        	if(checkCliente.getId() != idCliente) {
        		result.put("idResult", Constantes.ERROR_ADD_CLIENTE );
    			result.put("result", MessageBundle.getString("error_add_cliente_message"));
    			
    			return result;
        	}
        }
        
        Cliente cliente = new Cliente(idCliente);
        cliente.setId(idCliente);
        cliente.setCpfCnpj(cpfCnpj);
        cliente.setEndereco(endereco);
        
        clienteDao.merge(cliente);
        
        if(idCliente == null){
        	result.put("idResult", Constantes.SUCCES_ADD_CLIENTE );
			result.put("result", MessageBundle.getString("success_add_cliente_message"));
        }else{
        	result.put("idResult", Constantes.SUCCES_UPDATE_CLIENTE );
			result.put("result", MessageBundle.getString("success_update_cliente_message"));
        }
     
		return result;
	}
}

