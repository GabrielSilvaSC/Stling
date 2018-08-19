package br.com.stling.bean;

import br.com.stling.util.Constantes;
import br.com.stling.util.MessageBundle;

public class MobileResponse {
	
	private int idResult;
	private String result;
	private Object object;
	
	public MobileResponse(int idResult) {
		this(idResult, "");
	}
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	public MobileResponse(int idResult, String params) {
		super();
		this.idResult = idResult;
		switch (idResult) {
		case Constantes.SUCCESS:
			result = MessageBundle.getString("success_message");
			break;
		case Constantes.SUCCESS_LOGIN:
			result = MessageBundle.getString("success_login_message"); 
			break;
		case Constantes.ERROR_USER_OR_PASSWORD_INVALID:
			result = MessageBundle.getString("error_message");
			break;
		case Constantes.ERROR_UNAUTHORIZED:
			result = MessageBundle.getString("error_unauthorized_message");
			break;
		case Constantes.SUCCES_UPDATE_CLIENTE:
			result = MessageBundle.getString("success_update_cliente_message");
			break;
		case Constantes.SUCCES_ADD_CLIENTE:
			result = MessageBundle.getString("success_add_cliente_message");
			break;
		case Constantes.ERROR_ADD_CLIENTE:
			result = MessageBundle.getString("error_add_cliente_message");
			break;
		case Constantes.ERROR_PRODUTO_NOT_FOUND : 	
			result = MessageBundle.getString("error_produto_not_found");
			break;
		case Constantes.ERROR_CLIENTE_NOT_FOUND :
			result = MessageBundle.getString("error_cliente_not_found");
			break;
		default:
			break;
		}
		
	}
	
	public int getIdResult() {
		return idResult;
	}

	public void setIdResult(int idResult) {
		this.idResult = idResult;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
