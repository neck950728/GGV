package member.service;

import java.util.List;
import java.util.Map;

import member.bean.CartDTO;
import member.bean.HelpCenterCommentDTO;
import member.bean.HelpCenterDTO;
import member.bean.MemberDTO;
import member.bean.PaymentCompleteDTO;
import member.bean.PaymentDTO;
import member.bean.PaymentHistoryDTO;
import member.bean.TempMemberDTO;
import member.bean.WishListDTO;
import member.other.HelpCenterListWrapper;
import member.other.MyPageWrapper;

public interface MemberService {
	boolean duplicateCheck(String id);
	int phoneAuth(String phone);
	boolean phoneAuthCheck(int authNum, int inputted_authNum);
	void emailAuthForJoin(TempMemberDTO tempMemberDTO);
	String emailAuthCheckForJoin(String id, String email_auth_key);
	boolean loginCheck(String id, String pw);
	int emailAuth(String email);
	List<String> findID(String used_destination);
	boolean idIsExist(String id);
	MemberDTO selectMember(String id);
	void updatePassword(String id, String pw);
	void insertWishList(WishListDTO wishListDTO);
	void deleteWishList(WishListDTO wishListDTO);
	MyPageWrapper myPage(String id);
	Map<String, String> selectMovieInfoForMyPage(String movie_code);
	void deleteWatchedMovies(String reserve_code);
	void updateMember(MemberDTO memberDTO);
	List<CartDTO> selectCart(String id);
	int getCartCount(String id);
	int insertCart(CartDTO cartDTO);
	void deleteCart(CartDTO cartDTO);
	PaymentCompleteDTO paymentForCulture(PaymentDTO paymentDTO);
	void cancelPaymentForCulture(PaymentHistoryDTO paymentHistoryDTO);
	HelpCenterListWrapper selectHelpCenterList(String id, String type, String searchKeyword, int page);
	String getRandomCodeForHelpCenter();
	void insertHelpCenter(String id, String type, HelpCenterDTO helpCenterDTO);
	HelpCenterDTO selectHelpCenter(String article_code);
	HelpCenterDTO detailHelpCenter(String id, String article_code);
	List<HelpCenterCommentDTO> selectHelpCenterCommentList(String article_code);
	void insertHelpCenterComment(HelpCenterCommentDTO helpCenterCommentDTO);
	void updateHelpCenterComment(HelpCenterCommentDTO helpCenterCommentDTO);
	void deleteHelpCenterComment(String identification_code);
	void updateHelpCenter(HelpCenterDTO helpCenterDTO);
	void deleteHelpCenter(String article_code);
}