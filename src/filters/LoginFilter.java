package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managedBean.Managed;


/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// Obtengo el bean que representa el usuario desde el scope sesi�n
		Managed loginBean = (Managed) req.getSession().getAttribute(
				"MBean");

		// Proceso la URL que est� requiriendo el cliente
		String urlStr = req.getRequestURL().toString().toLowerCase();
		boolean noProteger = noProteger(urlStr);
//		System.out.println(urlStr + " - desprotegido=[" + noProteger + "]");

		// Si no requiere protecci�n contin�o normalmente.
		if (noProteger(urlStr)) {
			chain.doFilter(request, response);
			return;
		}

		// El usuario no est� logueado
		if (loginBean == null || !loginBean.isLogeado()) {
			res.sendRedirect(req.getContextPath() + "/login.xhtml");
			return;
		}

		// El recurso requiere protecci�n, pero el usuario ya est� logueado.
		chain.doFilter(request, response);
	}
	
	private boolean noProteger(String urlStr) {

		/*
		 * Este es un buen lugar para colocar y programar todos los patrones que
		 * creamos convenientes para determinar cuales de los recursos no
		 * requieren protecci�n. Sin duda que habr�a que crear un mecanizmo tal
		 * que se obtengan de un archivo de configuraci�n o algo que no requiera
		 * compilaci�n.
		 */
		  if (urlStr.endsWith("login.xhtml"))
		    return true;
		  if (urlStr.indexOf("/javax.faces.resource/") != -1)
		    return true;
		  if(urlStr.endsWith("headerbanner.jpg"))
			  return true;
		  if(urlStr.endsWith("icono.ico"))
			  return true;
		  if(urlStr.contentEquals("images"))
			  return true;
		  if(urlStr.contentEquals("includes"))
			  return true;
		  return false;
		}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
