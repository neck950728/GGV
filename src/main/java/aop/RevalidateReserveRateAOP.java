package aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.GenericXmlApplicationContext;

import admin.movie.bean.ScreeningInfoDTO;

@Aspect
public class RevalidateReserveRateAOP {
	/*
		@Autowired
		private SqlSessionTemplate sqlSession;
	*/
	private SqlSessionTemplate sqlSession = new GenericXmlApplicationContext("aop/applicationContext.xml").getBean("sqlSessionTemplate", SqlSessionTemplate.class);
	
	@Around("execution(* aop_*(..))")
	public Object revalidateReserveRate(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			Object result = joinPoint.proceed(); // pointcut 대상 메서드 실행
			/*
				if(result instanceof ModelAndView) {
					ModelAndView modelAndView = (ModelAndView)result;
				}
			*/
			
			return result;
		}finally {
			// int totalReservedCount = sqlSession.selectOne("mybatis.aopMapper.selectTotalReservedCount"); // NullPointerException(int totalReservedCount = null)
			Integer totalReservedCount = sqlSession.selectOne("mybatis.aopMapper.selectTotalReservedCount");
			
			List<String> reservedMovieCodeList = sqlSession.selectList("mybatis.aopMapper.selectReservedMovieCodeList");
			for(String reservedMovieCode : reservedMovieCodeList) {
				int reservedCount = 0;
				
				List<ScreeningInfoDTO> reservedMovieScreeningInfoList = sqlSession.selectList("mybatis.aopMapper.selectReservedMovieScreeningInfoList", reservedMovieCode);
				for(ScreeningInfoDTO reservedMovieScreeningInfo : reservedMovieScreeningInfoList) {
					reservedCount += reservedMovieScreeningInfo.getReserved_seat().split(", ").length;
				}
				
				Map<String, Object> param = new HashMap<>();
				param.put("movie_code", reservedMovieCode);
				param.put("reserveRate", (reservedCount * 1.0) / totalReservedCount * 100); // reservedCount * 1.0 : int  →  double 변환
				sqlSession.update("mybatis.aopMapper.updateReserveRate", param);
			}
			
			// ----------------------------------------------------------------------------------------------------------------------------------------------------------
			
			List<String> movieCodeList = sqlSession.selectList("mybatis.aopMapper.checkMovieReserveRate");
			for(String movie_code : movieCodeList) {
				int reserveCount = sqlSession.selectOne("mybatis.aopMapper.checkMovieReserveCount", movie_code);
				if(reserveCount == 0) {
					Map<String, Object> param = new HashMap<>();
					param.put("movie_code", movie_code);
					param.put("reserveRate", 0);
					
					sqlSession.update("mybatis.aopMapper.updateReserveRate", param);
				}
			}
		}
	}
}