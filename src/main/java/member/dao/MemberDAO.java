package member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import member.bean.CartDTO;
import member.bean.HelpCenterCommentDTO;
import member.bean.HelpCenterDTO;
import member.bean.MemberDTO;
import member.bean.PaymentHistoryDTO;
import member.bean.TempMemberDTO;
import member.bean.WatchedMovieDTO;
import member.bean.WishListDTO;
import movie.bean.ReserveDTO;
import movie.dao.MovieDAO;

@Repository
public class MemberDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int insertTempMember(TempMemberDTO tempMemberDTO) {
		return sqlSession.insert("mybatis.memberMapper.insertTempMember", tempMemberDTO);
	}
	
	public TempMemberDTO selectTempMember(String id) {
		return sqlSession.selectOne("mybatis.memberMapper.selectTempMember", id);
	}
	
	public void insertMember(MemberDTO memberDTO) {
		sqlSession.delete("mybatis.memberMapper.deleteTempMember", memberDTO.getId());
		sqlSession.insert("mybatis.memberMapper.insertMember", memberDTO);
	}
	
	public MemberDTO selectMember(String id) {
		MemberDTO memberDTO = sqlSession.selectOne("mybatis.memberMapper.selectMember", id);
		if(memberDTO != null) {
			memberDTO.setWishList(sqlSession.selectList("mybatis.memberMapper.selectWishList", id));
		}
		
		return memberDTO;
	}
	
	
	public List<MemberDTO> selectMemberList(String used_destination){
		Map<String, Object> param = new HashMap<>();
		param.put("used_destination", used_destination);
		
		return sqlSession.selectList("mybatis.memberMapper.selectMemberList", param);
	}
	
	public void updatePassword(String id, String pw) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("pw", pw);
		
		sqlSession.update("mybatis.memberMapper.updatePassword", param);
	}
	
	
	public void insertWishList(MovieDAO movieDAO, WishListDTO wishListDTO) {
		sqlSession.insert("mybatis.memberMapper.insertWishList", wishListDTO);
		movieDAO.addLike(wishListDTO.getMovie_code());
	}
	
	public void deleteWishList(MovieDAO movieDAO, WishListDTO wishListDTO) {
		sqlSession.delete("mybatis.memberMapper.deleteWishList", wishListDTO);
		movieDAO.subtractLike(wishListDTO.getMovie_code());
	}
	
	
	public List<WatchedMovieDTO> selectMyWatchedMovies(String id){
		return sqlSession.selectList("mybatis.memberMapper.selectMyWatchedMovies", id);
	}
	
	public void deleteMyWatchedMovies(String reserve_code){
		sqlSession.selectList("mybatis.memberMapper.deleteMyWatchedMovies", reserve_code);
	}
	
	public List<ReserveDTO> selectMyReserveList(String id){
		return sqlSession.selectList("mybatis.memberMapper.selectMyReserveList", id);
	}
	
	public List<PaymentHistoryDTO> selectMyPaymentHistoryList(String id){
		return sqlSession.selectList("mybatis.memberMapper.selectMyPaymentHistoryList", id);
	}
	
	public Map<String, String> selectMovieInfoForMyPage(String movie_code) {
		return sqlSession.selectOne("mybatis.memberMapper.selectMovieInfoForMyPage", movie_code);
	}
	
	public void updateMember(MemberDTO memberDTO) {
		sqlSession.update("mybatis.memberMapper.updateMember", memberDTO);
	}
	
	
	public List<CartDTO> selectCart(String id) {
		return sqlSession.selectList("mybatis.memberMapper.selectCart", id);
	}
	
	private int selectCartItemCount(CartDTO cartDTO) {
		try {
			return sqlSession.selectOne("mybatis.memberMapper.selectCartItemCount", cartDTO);
		}catch(NullPointerException e) {
			return 0;
		}
	}
	
	public int insertCart(CartDTO cartDTO) {
		int count = selectCartItemCount(cartDTO);
		if(count >= 10) {
			return 0;
		}else if(count > 0) {
			cartDTO.setCount(cartDTO.getCount() + count);
			sqlSession.update("mybatis.memberMapper.updateCart", cartDTO);
			return 1;
		}else {
			sqlSession.insert("mybatis.memberMapper.insertCart", cartDTO);
			return 1;
		}
	}
	
	public void deleteCart(CartDTO cartDTO) {
		sqlSession.insert("mybatis.memberMapper.deleteCart", cartDTO);
	}
	
	public void deleteCartList(List<CartDTO> cart_list) {
		Map<String, Object> param = new HashMap<>();
		param.put("cart_list", cart_list);
		
		sqlSession.delete("mybatis.memberMapper.deleteCartList", param);
	}
	
	public int orderCodeDuplicateCheck(String order_code) {
		return sqlSession.selectOne("mybatis.memberMapper.selectOrderCodeDuplicateCheck", order_code);
	}
	
	public void insertPaymentHistory(String order_code, List<CartDTO> cart_list) {
		Map<String, Object> param = new HashMap<>();
		param.put("order_code", order_code);
		param.put("cart_list", cart_list);
		
		sqlSession.insert("mybatis.memberMapper.insertPaymentHistory", param);
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String before_url = req.getHeader("Referer");
		if(before_url.contains("cart")) {
			deleteCartList(cart_list);
		}
	}
	
	public void deletePaymentHistory(PaymentHistoryDTO paymentHistoryDTO) {
		sqlSession.delete("mybatis.memberMapper.deletePaymentHistory", paymentHistoryDTO);
	}
	
	
	public int selectTotalHelpCenterCount(Map<String, Object> param) {
		return sqlSession.selectOne("mybatis.memberMapper.selectTotalHelpCenterCount", param);
	}
	
	public List<HelpCenterDTO> selectCurPageHelpCenterList(Map<String, Object> param) {
		return sqlSession.selectList("mybatis.memberMapper.selectCurPageHelpCenterList", param);
	}
	
	public int articleCodeDuplicateCheck(String article_code) {
		return sqlSession.selectOne("mybatis.memberMapper.selectArticleCodeDuplicateCheck", article_code);
	}
	
	public void insertHelpCenter(HelpCenterDTO helpCenterDTO) {
		sqlSession.insert("mybatis.memberMapper.insertHelpCenter", helpCenterDTO);
	}
	
	public HelpCenterDTO selectHelpCenter(String article_code) {
		return sqlSession.selectOne("mybatis.memberMapper.selectHelpCenter", article_code);
	}
	
	public int selectHelpCenterHits(Map<String, Object> param) {
		return sqlSession.selectOne("mybatis.memberMapper.selectHelpCenterHits", param);
	}
	
	public void insertHelpCenterHits(Map<String, Object> param) {
		sqlSession.insert("mybatis.memberMapper.insertHelpCenterHits", param);
		sqlSession.update("mybatis.memberMapper.addHelpCenterHitsCount", param.get("article_code"));
	}
	
	public List<HelpCenterCommentDTO> selectHelpCenterCommentList(String article_code) {
		return sqlSession.selectList("mybatis.memberMapper.selectHelpCenterCommentList", article_code);
	}
	
	public void insertHelpCenterComment(HelpCenterCommentDTO helpCenterCommentDTO) {
		sqlSession.insert("mybatis.memberMapper.insertHelpCenterComment", helpCenterCommentDTO);
	}
	
	public void updateHelpCenterComment(HelpCenterCommentDTO helpCenterCommentDTO) {
		sqlSession.update("mybatis.memberMapper.updateHelpCenterComment", helpCenterCommentDTO);
	}
	
	public void deleteOneHelpCenterComment(String identification_code) {
		sqlSession.delete("mybatis.memberMapper.deleteOneHelpCenterComment", identification_code);
	}
	
	public void updateHelpCenter(HelpCenterDTO helpCenterDTO) {
		sqlSession.update("mybatis.memberMapper.updateHelpCenter", helpCenterDTO);
	}
	
	public void deleteHelpCenter(String article_code) {
		sqlSession.delete("mybatis.memberMapper.deleteHelpCenter", article_code);
		sqlSession.delete("mybatis.memberMapper.deleteHelpCenterHits", article_code);
		sqlSession.delete("mybatis.memberMapper.deleteHelpCenterComment", article_code);
	}
}