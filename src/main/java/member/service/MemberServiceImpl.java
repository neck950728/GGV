package member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import member.bean.CartDTO;
import member.bean.HelpCenterCommentDTO;
import member.bean.HelpCenterDTO;
import member.bean.MemberDTO;
import member.bean.PaymentCompleteDTO;
import member.bean.PaymentDTO;
import member.bean.PaymentHistoryDTO;
import member.bean.TempMemberDTO;
import member.bean.WishListDTO;
import member.dao.MemberDAO;
import member.other.HelpCenterListWrapper;
import member.other.HelpCenterPaging;
import member.other.MyPageWrapper;
import member.other.Phone;
import member.other.SMTP;
import movie.dao.MovieDAO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private MovieDAO movieDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Phone phone;
	
	@Autowired
	private SMTP smtp;
	
	private String getRandomOrderCode() {
		Random rnd = new Random();
		String order_code = "";
		
		for(int i = 1; i <= 19; i++) {
			if(i % 5 == 0) {
				order_code += "-";
			}else {
			    if(rnd.nextBoolean()) { // true 또는 false를 랜덤으로 반환
			    	order_code += (char)((int)(Math.random() * (90 - 65 + 1)) + 65); // A(65) ~ Z(90)
			    }else {
			    	order_code += rnd.nextInt(10); // nextInt(10) : 0 ~ 9까지의 정수를 랜덤으로 반환
			    }
			}
		}
		
		int result = memberDAO.orderCodeDuplicateCheck(order_code);
		if(result > 0) {
			order_code = getRandomOrderCode();
		}
		
		return order_code;
	}
	
	private String getRandomArticleCode() {
		Random rnd = new Random();
		String article_code = "";
		
		for(int i = 1; i <= 19; i++) {
			if(i % 5 == 0) {
				article_code += "-";
			}else {
			    if(rnd.nextBoolean()) {
			    	article_code += (char)((int)(Math.random() * (90 - 65 + 1)) + 65);
			    }else {
			    	article_code += rnd.nextInt(10);
			    }
			}
		}
		
		int result = memberDAO.articleCodeDuplicateCheck(article_code);
		if(result > 0) {
			article_code = getRandomOrderCode();
		}
		
		return article_code;
	}
	
	
	@Override
	public boolean duplicateCheck(String id) {
		boolean result = false;
		
		MemberDTO memberDTO = memberDAO.selectMember(id);
		TempMemberDTO tempMemberDTO = memberDAO.selectTempMember(id);
		
		if(memberDTO != null || tempMemberDTO != null) {
			result = true;
		}
		
		return result;
	}
	
	@Override
	public int phoneAuth(String phoneNum) {
		return phone.send(phoneNum);
	}
	
	@Override
	public boolean phoneAuthCheck(int authNum, int inputted_authNum) {
		boolean result;
		
		try {
			if(authNum == inputted_authNum) {
				result = true;
			}else {
				result = false;
			}
		}catch(NumberFormatException e) {
			result = false;
		}
		
		return result;
	}
	
	@Override
	public void emailAuthForJoin(TempMemberDTO tempMemberDTO) {
		tempMemberDTO.setPw(passwordEncoder.encode(tempMemberDTO.getPw())); // 비밀번호 암호화
		
		String email_auth_key = UUID.randomUUID().toString();
		tempMemberDTO.setEmail_auth_key(email_auth_key);
		
		memberDAO.insertTempMember(tempMemberDTO);
		smtp.send_mail(tempMemberDTO.getEmail(), tempMemberDTO.getId(), email_auth_key);
	}
	
	@Transactional
	@Override
	public String emailAuthCheckForJoin(String id, String email_auth_key) {
		String view;
		MemberDTO memberDTO;
		TempMemberDTO tempMemberDTO;
		
		try {
			tempMemberDTO = memberDAO.selectTempMember(id);
			
			if(email_auth_key.equals(tempMemberDTO.getEmail_auth_key())) {
				memberDTO = tempMemberDTO;
				memberDAO.insertMember(memberDTO);
				
				view = "user/member/join/joinForm/emailAuthSuccess";
			}else {
				view = "user/member/join/joinForm/emailAuthFail";
			}
		}catch(NullPointerException e) {
			try {
				memberDTO = memberDAO.selectMember(id);
				
				if(email_auth_key.equals(memberDTO.getEmail_auth_key())) {
					view = "user/member/join/joinForm/emailAuthAlreadySucceeded";
				}else {
					view = "user/member/join/joinForm/emailAuthFail";
				}
			}catch(NullPointerException exception) {
				view = "user/member/join/joinForm/emailAuthFail";
			}
		}
		
		return view;
	}
	
	@Override
	public boolean loginCheck(String id, String pw) {
		boolean result = false;
		
		try {
			MemberDTO memberDTO = memberDAO.selectMember(id);
			if(passwordEncoder.matches(pw, memberDTO.getPw())) {
				result = true;
			}
		}catch(NullPointerException e) {
			result = false;
		}
		
		return result;
	}
	
	@Override
	public int emailAuth(String email) {
		return smtp.send_mail(email);
	}
	
	@Override
	public List<String> findID(String used_destination) {
		List<String> foundIDList = new ArrayList<>();
		
		List<MemberDTO> memberList = memberDAO.selectMemberList(used_destination);
		for(MemberDTO memberDTO : memberList) {
			foundIDList.add(memberDTO.getId());
		}
		
		return foundIDList;
	}
	
	@Override
	public boolean idIsExist(String id) {
		if(memberDAO.selectMember(id) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public MemberDTO selectMember(String id) {
		return memberDAO.selectMember(id);
	}
	
	@Override
	public void updatePassword(String id, String pw) {
		memberDAO.updatePassword(id, passwordEncoder.encode(pw));
	}
	
	@Transactional
	@Override
	public void insertWishList(WishListDTO wishListDTO) {
		memberDAO.insertWishList(movieDAO, wishListDTO);
	}
	
	@Transactional
	@Override
	public void deleteWishList(WishListDTO wishListDTO) {
		memberDAO.deleteWishList(movieDAO, wishListDTO);
	}
	
	@Override
	public MyPageWrapper myPage(String id) {
		MyPageWrapper myPageWrapper = new MyPageWrapper();
		myPageWrapper.setWatchedMovies(memberDAO.selectMyWatchedMovies(id));
		myPageWrapper.setReserveList(memberDAO.selectMyReserveList(id));
		myPageWrapper.setPaymentList(memberDAO.selectMyPaymentHistoryList(id));
		
		return myPageWrapper;
	}
	
	@Override
	public Map<String, String> selectMovieInfoForMyPage(String movie_code) {
		return memberDAO.selectMovieInfoForMyPage(movie_code);
	}
	
	@Override
	public void deleteWatchedMovies(String reserve_code) {
		memberDAO.deleteMyWatchedMovies(reserve_code);
	}
	
	@Override
	public void updateMember(MemberDTO memberDTO) {
		if(!memberDTO.getPw().isEmpty()) {
			memberDTO.setPw(passwordEncoder.encode(memberDTO.getPw()));
		}
		
		memberDAO.updateMember(memberDTO);
	}
	
	@Override
	public List<CartDTO> selectCart(String id) {
		return memberDAO.selectCart(id);
	}
	
	@Override
	public int getCartCount(String id) {
		int total_count = 0;
		
		List<CartDTO> cart_list = memberDAO.selectCart(id);
		for(CartDTO cartDTO : cart_list) {
			total_count += cartDTO.getCount();
		}
		
		return total_count;
	}
	
	@Override
	public int insertCart(CartDTO cartDTO) {
		return memberDAO.insertCart(cartDTO);
	}
	
	@Override
	public void deleteCart(CartDTO cartDTO) {
		memberDAO.deleteCart(cartDTO);
	}
	
	@Transactional
	@Override
	public PaymentCompleteDTO paymentForCulture(PaymentDTO paymentDTO) {
		String order_code = getRandomOrderCode();
		
		memberDAO.insertPaymentHistory(order_code, paymentDTO.getCart_list());
		
		PaymentCompleteDTO paymentCompleteDTO = new PaymentCompleteDTO();
		paymentCompleteDTO.setOrder_code(order_code);
		paymentCompleteDTO.setCart_list(paymentDTO.getCart_list());
		paymentCompleteDTO.setPayment_price(paymentDTO.getPayment_price());
		
		return paymentCompleteDTO;
	}
	
	@Override
	public void cancelPaymentForCulture(PaymentHistoryDTO paymentHistoryDTO) {
		memberDAO.deletePaymentHistory(paymentHistoryDTO);
	}
	
	@Override
	public HelpCenterListWrapper selectHelpCenterList(String id, String type, String searchKeyword, int page) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("type", type);
		param.put("searchKeyword", searchKeyword);
		
		HelpCenterPaging paging = new HelpCenterPaging(page, memberDAO.selectTotalHelpCenterCount(param));
		param.put("startNum", paging.getStartNum());
		param.put("endNum", paging.getEndNum());
		
		HelpCenterListWrapper helpCenterListWrapper = new HelpCenterListWrapper();
		helpCenterListWrapper.setPaging(paging);
		helpCenterListWrapper.setHelpCenterList(memberDAO.selectCurPageHelpCenterList(param));
		
		return helpCenterListWrapper;
	}
	
	@Override
	public String getRandomCodeForHelpCenter() {
		return getRandomArticleCode();
	}
	
	@Override
	public void insertHelpCenter(String id, String type, HelpCenterDTO helpCenterDTO) {
		helpCenterDTO.setId(id);
		if(type.equalsIgnoreCase("private")) { helpCenterDTO.setIsPrivate("TRUE"); }
		memberDAO.insertHelpCenter(helpCenterDTO);
	}
	
	@Override
	public HelpCenterDTO selectHelpCenter(String article_code) {
		return memberDAO.selectHelpCenter(article_code);
	}
	
	@Transactional
	@Override
	public HelpCenterDTO detailHelpCenter(String id, String article_code) {
		HelpCenterDTO helpCenterDTO = memberDAO.selectHelpCenter(article_code);
		
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("article_code", article_code);
		
		if(!id.equals(helpCenterDTO.getId()) && memberDAO.selectHelpCenterHits(param) < 1) {
			memberDAO.insertHelpCenterHits(param);
			helpCenterDTO.setHits(helpCenterDTO.getHits() + 1);
		}
		
		return helpCenterDTO;
	}
	
	@Override
	public List<HelpCenterCommentDTO> selectHelpCenterCommentList(String article_code) {
		return memberDAO.selectHelpCenterCommentList(article_code);
	}
	
	@Override
	public void insertHelpCenterComment(HelpCenterCommentDTO helpCenterCommentDTO) {
		memberDAO.insertHelpCenterComment(helpCenterCommentDTO);
	}
	
	@Override
	public void updateHelpCenterComment(HelpCenterCommentDTO helpCenterCommentDTO) {
		memberDAO.updateHelpCenterComment(helpCenterCommentDTO);
	}
	
	@Override
	public void deleteHelpCenterComment(String identification_code) {
		memberDAO.deleteOneHelpCenterComment(identification_code);
	}
	
	@Override
	public void updateHelpCenter(HelpCenterDTO helpCenterDTO) {
		memberDAO.updateHelpCenter(helpCenterDTO);
	}
	
	@Transactional
	@Override
	public void deleteHelpCenter(String article_code) {
		memberDAO.deleteHelpCenter(article_code);
	}
}