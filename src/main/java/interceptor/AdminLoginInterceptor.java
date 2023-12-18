package interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
	private String[] refererMeaninglessPages = {"reserve"};
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("id") == null) {
			for(String refererMeaninglessPage : refererMeaninglessPages) {
				if(request.getRequestURL().toString().contains(refererMeaninglessPage)) {
					session.setAttribute("refererMeaninglessPage", request.getRequestURL().toString());
					break;
				}
			}
			
			String isAJAX = request.getHeader("x-requested-with");
			if(isAJAX != null && isAJAX.equalsIgnoreCase("XmlHttpRequest")) {
				String responseToAjax = "requireLogin_default";
				
				if(request.getHeader("loginInterceptor") != null) {
					switch(request.getHeader("loginInterceptor")) {
						case "confirm":
							responseToAjax = "requireLogin_confirm";
							break;
						case "childWindow":
							responseToAjax = "requireLogin_childWindow";
							break;
					}
				}
				
				PrintWriter out = response.getWriter();
				// out.println("require_login"); println 메서드를 사용하면 개행 문자 때문에 문자열 비교가 번거로워짐
				out.print(responseToAjax);
				out.flush();
			}else {
				response.sendRedirect(request.getContextPath() + "/member/loginForm");
			}
			
			return false;
		}else if(!(session.getAttribute("id").toString().equalsIgnoreCase("admin"))) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}
}