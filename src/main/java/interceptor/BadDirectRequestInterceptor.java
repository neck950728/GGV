package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BadDirectRequestInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Referer : 현재 웹 페이지가 어떤 웹 페이지로부터 요청된 것인지에 대한 정보(URL)
		if(request.getHeader("Referer") == null) { // 주소창을 통한 요청은 최초 요청으로 간주되기 때문에 Referer이 존재하지 않는다.
			((HttpServletResponse)response).sendRedirect(request.getContextPath() + "/");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}
}