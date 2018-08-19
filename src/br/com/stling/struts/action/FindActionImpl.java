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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.stling.bean.MobileResponse;
import br.com.stling.bean.Produto;
import br.com.stling.dao.ProdutoDao;
import br.com.stling.stream.adapter.HibernateProxyTypeAdapter;
import br.com.stling.util.Constantes;

/**
 *
 * @author eswar@vaannila.com
 */
public abstract class FindActionImpl extends HttpServlet {

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
	
	protected String findProdutoById(HttpServletRequest request) throws Exception {
		String json;
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();
		ProdutoDao produtoDao = new ProdutoDao();
		Integer id = Integer.valueOf(request.getParameter("produto.id"));
		Produto produto = produtoDao.findById(id);
		
		if(produto != null){
			json = gson.toJson(produto); // ==> json is [1,2,3,4,5]
		}else{
			json = gson.toJson(new MobileResponse(Constantes.ERROR_PRODUTO_NOT_FOUND));
		}
		return json;
	}
	
	protected String findProdutoByDescricao(HttpServletRequest request) throws Exception {
		String json;
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();
		ProdutoDao produtoDao = new ProdutoDao();
		String descricao = request.getParameter("produto.descricao");
		Produto produto = produtoDao.findByDescricao(descricao);
		
		if(produto != null){
			json = gson.toJson(produto); // ==> json is [1,2,3,4,5]
		}else{
			json = gson.toJson(new MobileResponse(Constantes.ERROR_PRODUTO_NOT_FOUND));
		}
		return json;
	}
}
