package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

// - 테스트용 필터 -
public class TestBadDirectRequestFilter implements Filter {
	private List<String> excludedUrls = new ArrayList<>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		for(String excludedUrl : filterConfig.getInitParameter("excludedUrls").split(",")) {
			excludedUrls.add(excludedUrl.trim());
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest h_req = (HttpServletRequest)request;
		if(!excludedUrls.contains(h_req.getServletPath())) {
			String user_id = (String)h_req.getSession().getAttribute("id");
			String param_id = request.getParameter("id");
			
			if(param_id != null) {
				if(user_id == null || !user_id.equalsIgnoreCase(param_id)){
					System.out.println("- Bad Direct Request! -");
					System.out.println(h_req.getRequestURL());
					System.out.println("user_id : " + user_id);
					System.out.println("param_id : " + param_id);
				}
			}
		}
		
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		
	}
}