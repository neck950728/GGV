package member.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
import member.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/member/join/joinFormMain")
	public String join_joinFormMain() {
		return "user/member/join/joinFormMain";
	}
	
	@RequestMapping(value = "/member/join/joinForm")
	public String join_joinForm() {
		return "user(except_include_config)/member/join/joinForm/form";
	}

	@RequestMapping(value = "/member/join/joinForm/duplicateCheck")
	@ResponseBody
	public boolean join_joinForm_duplicateCheck(@RequestParam("id")String id) {
		return memberService.duplicateCheck(id);
	}
	
	@RequestMapping(value = "/member/join/joinForm/phoneAuth")
	@ResponseBody
	public void join_joinForm_phoneAuth(HttpSession session, @RequestParam("identification_value")String identification_value, @RequestParam("phone")String phone) {
		session.setAttribute(identification_value, memberService.phoneAuth(phone));
	}
	
	@RequestMapping(value = "/member/join/joinForm/phoneAuthCheck")
	@ResponseBody
	public boolean join_joinForm_phoneAuthCheck(HttpSession session, @RequestParam("identification_value")String identification_value, @RequestParam("inputted_authNum")String inputted_authNum) {
		return memberService.phoneAuthCheck((int)session.getAttribute(identification_value), Integer.parseInt(inputted_authNum));
	}
	
	@RequestMapping(value = "/member/join/joinForm/unload")
	@ResponseBody
	public void join_joinForm_unload(HttpSession session, @RequestParam("identification_value")String identification_value) {
		session.removeAttribute(identification_value);
	}

	@RequestMapping(value = "/member/join/joinForm/emailAuth")
	public String join_joinForm_emailAuth(TempMemberDTO tempMemberDTO) {
		memberService.emailAuthForJoin(tempMemberDTO);
		
		return "user/member/join/joinForm/emailAuth";
	}
	
	@RequestMapping(value = "/member/join/joinForm/emailAuthCheck")
	public String join_joinForm_emailAuthCheck(@RequestParam("id")String id, @RequestParam("email_auth_key")String email_auth_key) {
		return memberService.emailAuthCheckForJoin(id, email_auth_key);
	}
	
	
	@RequestMapping(value = "/member/loginForm")
	public String loginForm(HttpServletRequest request, HttpSession session) {
		session.setAttribute("referer", request.getHeader("Referer"));
		
		return "user/member/loginForm/form";
	}
	
	@RequestMapping(value = "/member/loginForm/loginCheck")
	@ResponseBody
	public boolean loginForm_loginCheck(@RequestParam("id")String id, @RequestParam("pw")String pw) {
		return memberService.loginCheck(id, pw);
	}
	
	@RequestMapping(value = "/member/loginForm/login")
	public String loginForm_login(HttpServletRequest request, HttpSession session, @RequestParam("id")String id) {
		session.setAttribute("id", id);
		
		// 이전 페이지 복귀
		try {
			String refererMeaninglessPage = (String)session.getAttribute("refererMeaninglessPage"); // LoginInterceptor
			String referer = (String)session.getAttribute("referer");
			if(referer != null) {
				if(refererMeaninglessPage != null) {
					return "redirect:" + refererMeaninglessPage;
				}else {
					if(referer.contains("loginForm") || referer.contains("emailAuthCheck")) {
						referer = "/";
					}else if(referer.contains("commentModule")) {
						referer = referer.replace("commentModule", "comment");
					}
					
					return "redirect:" + referer;
				}
			}else {
				return "redirect:/";
			}
		}finally {
			session.setAttribute("refererMeaninglessPage", null);
		}
	}
	
	@RequestMapping(value = "/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();

		return "redirect:/";
	}
	
	@RequestMapping(value = "/member/loginForm/findID")
	public String loginForm_findID() {
		return "user(except_include_config)/member/loginForm/findID_window";
	}
	
	@RequestMapping(value = "/member/loginForm/findPW")
	public String loginForm_findPW() {
		return "user(except_include_config)/member/loginForm/findPW_window";
	}
	
	@RequestMapping(value = "/member/loginForm/findID/phoneAuth")
	@ResponseBody
	public int loginForm_findID_phoneAuth(@RequestParam("destination")String phone) {
		return memberService.phoneAuth(phone);
	}
	
	@RequestMapping(value = "/member/loginForm/findID/emailAuth")
	@ResponseBody
	public int loginForm_findID_emailAuth(@RequestParam("destination")String email) {
		return memberService.emailAuth(email);
	}
	
	@RequestMapping(value = "/member/loginForm/findID/successAuth")
	@ResponseBody
	public List<String> loginForm_findID_successAuth(@RequestParam("used_destination")String used_destination) {
		return memberService.findID(used_destination);
	}
	
	@RequestMapping(value = "/member/loginForm/findPW/idIsExist")
	@ResponseBody
	public boolean loginForm_findPW_idIsExist(@RequestParam("id")String id) {
		return memberService.idIsExist(id);
	}
	
	@RequestMapping(value = "/member/loginForm/findPW/phoneAuth")
	@ResponseBody
	public int loginForm_findPW_phoneAuth(@RequestParam("id")String id, @RequestParam("destination")String phone) {
		MemberDTO memberDTO = memberService.selectMember(id);
		if(memberDTO.getPhone().equals(phone)) {
			return memberService.phoneAuth(phone);
		}else {
			return -1;
		}
	}
	
	@RequestMapping(value = "/member/loginForm/findPW/emailAuth")
	@ResponseBody
	public int loginForm_findPW_emailAuth(@RequestParam("id")String id, @RequestParam("destination")String email) {
		MemberDTO memberDTO = memberService.selectMember(id);
		if(memberDTO.getEmail().equals(email)) {
			return memberService.emailAuth(email);
		}else {
			return -1;
		}
	}
	
	@RequestMapping(value = "/member/loginForm/findPW/updatePassword")
	@ResponseBody
	public void loginForm_findPW_updatePassword(HttpServletRequest req, @RequestParam("id")String id, @RequestParam("pw")String pw) {
		memberService.updatePassword(id, pw);
	}
	
	
	@RequestMapping(value = "/member/getMemberInfo")
	@ResponseBody
	public MemberDTO getMemberInfo(HttpSession session) {
		MemberDTO memberDTO = null;
		
		if(session.getAttribute("id") != null) {
			memberDTO = memberService.selectMember((String)session.getAttribute("id"));
		}
		
		return memberDTO;
	}
	
	
	@RequestMapping(value = "/member/wishList/insertWishList")
	@ResponseBody
	public void wishList_insertWishList(WishListDTO wishListDTO) {
		memberService.insertWishList(wishListDTO);
	}
	
	@RequestMapping(value = "/member/wishList/deleteWishList")
	@ResponseBody
	public void wishList_deleteWishList(WishListDTO wishListDTO) {
		memberService.deleteWishList(wishListDTO);
	}
	
	
	@RequestMapping(value = "/member/myPage")
	public ModelAndView myPage(HttpServletRequest req, HttpSession session) {
		String id = (String)session.getAttribute("id");
		MyPageWrapper myPageWrapper = memberService.myPage(id);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", req.getParameter("type"));
		modelAndView.addObject("watchedMovies_json", new Gson().toJson(myPageWrapper.getWatchedMovies()));
		modelAndView.addObject("reserveList_json", new Gson().toJson(myPageWrapper.getReserveList()));
		modelAndView.addObject("paymentList_json", new Gson().toJson(myPageWrapper.getPaymentList()));
		modelAndView.setViewName("user/member/myPage");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/myPage/selectMovieInfoForMyPage")
	@ResponseBody
	public Map<String, String> myPage_selectMovieInfoForMyPage(@RequestParam("movie_code")String movie_code) {
		return memberService.selectMovieInfoForMyPage(movie_code);
	}
	
	@RequestMapping(value = "/member/myPage/deleteWatchedMovies")
	@ResponseBody
	public void myPage_deleteWatchedMovies(@RequestParam("reserve_code")String reserve_code) {
		memberService.deleteWatchedMovies(reserve_code);
	}
	
	@RequestMapping(value = "/member/myPage/changeInfo/emailAuth")
	@ResponseBody
	public int myPage_changeInfo_emailAuth(@RequestParam("email")String email) {
		return memberService.emailAuth(email);
	}
	
	@RequestMapping(value = "/member/myPage/changeInfo/phoneAuth")
	@ResponseBody
	public int myPage_changeInfo_phoneAuth(@RequestParam("phone")String phone) {
		return memberService.phoneAuth(phone);
	}
	
	@RequestMapping(value = "/member/myPage/changeInfo/request")
	@ResponseBody
	public void myPage_changeInfo_request(MemberDTO memberDTO) {
		memberService.updateMember(memberDTO);
	}
	
	
	@RequestMapping(value = "/member/culture")
	public ModelAndView culture() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/member/culture/main");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/culture/more")
	public ModelAndView culture_more(@RequestParam("type")String type) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", type);
		modelAndView.setViewName("user/member/culture/more");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/culture/detail")
	public ModelAndView culture_detail(@RequestParam("type")String type, @RequestParam("item")String item) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", type);
		modelAndView.addObject("item", item);
		modelAndView.setViewName("user/member/culture/detail");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/culture/cart")
	public ModelAndView culture_cart(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("cart_json", new Gson().toJson(memberService.selectCart((String)session.getAttribute("id"))));
		modelAndView.setViewName("user/member/culture/cart/list");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/culture/cart/getCartCount")
	@ResponseBody
	public int culture_cart_getCartCount(@RequestParam("id")String id) {
		return memberService.getCartCount(id);
	}
	
	@RequestMapping(value = "/member/culture/cart/insert")
	@ResponseBody
	public int culture_cart_insert(CartDTO cartDTO) {
		return memberService.insertCart(cartDTO);
	}
	
	@RequestMapping(value = "/member/culture/cart/delete")
	@ResponseBody
	public void culture_cart_delete(CartDTO cartDTO) {
		memberService.deleteCart(cartDTO);
	}
	
	@RequestMapping(value = "/member/culture/cart/payment")
	@ResponseBody
	public PaymentCompleteDTO culture_cart_payment(@RequestBody PaymentDTO paymentDTO) {
		return memberService.paymentForCulture(paymentDTO);
	}
	
	@RequestMapping(value = "/member/culture/cart/payment/complete")
	public ModelAndView culture_cart_payment_complete(PaymentCompleteDTO paymentCompleteDTO) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("paymentComplete", paymentCompleteDTO);
		modelAndView.setViewName("user/member/culture/cart/payment/complete");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/culture/cart/payment/cancel")
	@ResponseBody
	public void culture_cart_payment_cancel(PaymentHistoryDTO paymentHistoryDTO) {
		memberService.cancelPaymentForCulture(paymentHistoryDTO);
	}
	
	
	private String getHelpCenterType(HttpServletRequest req) {
		if(req.getRequestURI().contains("public")) {
			return "public";
		}else {
			return "private";
		}
	}
	
	private void excludeUnusedFiles(HttpServletRequest req, HelpCenterDTO helpCenterDTO) {
		String contextRoot = new HttpServletRequestWrapper(req).getRealPath("/");
		File before_fileRoot = new File(contextRoot + "resources" + File.separator + "summernote" + File.separator + "uploadImages" + File.separator + helpCenterDTO.getArticle_code());
		File after_fileRoot = new File(contextRoot + "resources" + File.separator + "summernote" + File.separator + "uploadImages" + File.separator + "Temp_" + helpCenterDTO.getArticle_code());
		
		if(helpCenterDTO.getUploadImages() != null) {
			after_fileRoot.mkdirs();
			
			for(String uploadImage : helpCenterDTO.getUploadImages()) {
				File upload_image = new File(before_fileRoot, uploadImage);
				upload_image.renameTo(new File(after_fileRoot, uploadImage));
			}
			
			FileUtils.deleteQuietly(before_fileRoot);
			after_fileRoot.renameTo(before_fileRoot);
		}else {
			FileUtils.deleteQuietly(before_fileRoot);
		}
	}
	
	@RequestMapping(value = {"/member/helpCenter/public", "/member/helpCenter/private"})
	public ModelAndView helpCenter_main(HttpServletRequest req, HttpSession session) {
		String id = (String)session.getAttribute("id");
		String type = getHelpCenterType(req);
		int page;
		
		try {
			page = Integer.parseInt(req.getParameter("page"));
		}catch(NumberFormatException e) {
			page = 1;
		}
		
		HelpCenterListWrapper helpCenterListWrapper = memberService.selectHelpCenterList(id, type, req.getParameter("searchKeyword"), page);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", type);
		modelAndView.addObject("paging", helpCenterListWrapper.getPaging());
		modelAndView.addObject("helpCenterList", helpCenterListWrapper.getHelpCenterList());
		modelAndView.setViewName("user/member/helpCenter/main");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/helpCenter/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String helpCenter_uploadSummernoteImageFile(HttpServletRequest req, @RequestParam("file")MultipartFile multipartFile) {
		JsonObject jsonObject = new JsonObject();
		
		String contextRoot = new HttpServletRequestWrapper(req).getRealPath("/");
		String article_code = req.getParameter("article_code");
		String fileRoot = contextRoot + "resources" + File.separator + "summernote" + File.separator + "uploadImages" + File.separator + article_code;
		
		String originalFileName = multipartFile.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		String savedFileName = UUID.randomUUID() + extension;
		
		File target = new File(fileRoot, savedFileName);
		
		try {
			new File(fileRoot).mkdirs();
			multipartFile.transferTo(target);
			jsonObject.addProperty("url", req.getContextPath() + "/resources/summernote/uploadImages/" + article_code + "/" + savedFileName);
			jsonObject.addProperty("imgName", savedFileName);
			jsonObject.addProperty("responseCode", "success");
		}catch(IOException e) {
			e.printStackTrace();
			
			FileUtils.deleteQuietly(target);
			jsonObject.addProperty("responseCode", "error");
		}
		
		return jsonObject.toString();
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/writeForm", "/member/helpCenter/private/writeForm"})
	public ModelAndView helpCenter_writeForm(HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", getHelpCenterType(req));
		modelAndView.addObject("article_code", memberService.getRandomCodeForHelpCenter());
		modelAndView.setViewName("user/member/helpCenter/writeForm");
		
		return modelAndView;
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/writeForm/write", "/member/helpCenter/private/writeForm/write"})
	public String helpCenter_writeForm_write(HttpServletRequest req, HttpSession session, HelpCenterDTO helpCenterDTO) {
		String id = (String)session.getAttribute("id");
		String type = getHelpCenterType(req);
		memberService.insertHelpCenter(id, type, helpCenterDTO);
		excludeUnusedFiles(req, helpCenterDTO);
		
		return "redirect:/member/helpCenter/" + type + "/detail?article_code=" + helpCenterDTO.getArticle_code();
	}
	
	@RequestMapping(value = "/member/helpCenter/writeForm/unload")
	@ResponseBody
	public void helpCenter_writeForm_unload(HttpServletRequest req, @RequestParam("article_code")String article_code) {
		String contextRoot = new HttpServletRequestWrapper(req).getRealPath("/");
		String fileRoot = contextRoot + "resources" + File.separator + "summernote" + File.separator + "uploadImages" + File.separator + article_code;
		FileUtils.deleteQuietly(new File(fileRoot));
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/modifyForm", "/member/helpCenter/private/modifyForm"})
	public ModelAndView helpCenter_modifyForm(HttpServletRequest req, HttpSession session, @RequestParam("article_code")String article_code) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", getHelpCenterType(req));
		modelAndView.addObject("helpCenter", memberService.selectHelpCenter(article_code));
		modelAndView.setViewName("user/member/helpCenter/modifyForm");
		
		return modelAndView;
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/modifyForm/modify", "/member/helpCenter/private/modifyForm/modify"})
	public String helpCenter_modifyForm_modify(HttpServletRequest req, HttpSession session, HelpCenterDTO helpCenterDTO) {
		memberService.updateHelpCenter(helpCenterDTO);
		excludeUnusedFiles(req, helpCenterDTO);
		
		return "redirect:/member/helpCenter/" + getHelpCenterType(req) + "/detail?article_code=" + helpCenterDTO.getArticle_code();
	}
	
	@RequestMapping(value = "/member/helpCenter/modifyForm/unload")
	@ResponseBody
	public void helpCenter_modifyForm_unload(HttpServletRequest req, @RequestBody HelpCenterDTO helpCenterDTO) {
		String contextRoot = new HttpServletRequestWrapper(req).getRealPath("/");
		String fileRoot = contextRoot + "resources" + File.separator + "summernote" + File.separator + "uploadImages" + File.separator + helpCenterDTO.getArticle_code();
		
		for(String uploadImg : helpCenterDTO.getUploadImages()) {
			new File(fileRoot, uploadImg).delete();
		}
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/detail", "/member/helpCenter/private/detail"})
	public ModelAndView helpCenter_detail(HttpServletRequest req, HttpSession session, @RequestParam("article_code")String article_code) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", getHelpCenterType(req));
		modelAndView.addObject("helpCenter", memberService.detailHelpCenter((String)session.getAttribute("id"), article_code));
		modelAndView.addObject("helpCenterCommentList", memberService.selectHelpCenterCommentList(article_code));
		
		modelAndView.setViewName("user/member/helpCenter/detail");
		
		return modelAndView;
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/detail/insertComment", "/member/helpCenter/private/detail/insertComment"})
	public String helpCenter_detail_insertComment(HttpServletRequest req, HelpCenterCommentDTO helpCenterCommentDTO) {
		helpCenterCommentDTO.setIdentification_code(UUID.randomUUID().toString());
		memberService.insertHelpCenterComment(helpCenterCommentDTO);
		
		return "redirect:/member/helpCenter/" + getHelpCenterType(req) + "/detail?article_code=" + helpCenterCommentDTO.getArticle_code(); 
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/detail/updateComment", "/member/helpCenter/private/detail/updateComment"})
	public String helpCenter_detail_updateComment(HttpServletRequest req, HelpCenterCommentDTO helpCenterCommentDTO) {
		memberService.updateHelpCenterComment(helpCenterCommentDTO);
		
		return "redirect:/member/helpCenter/" + getHelpCenterType(req) + "/detail?article_code=" + helpCenterCommentDTO.getArticle_code(); 
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/detail/deleteComment", "/member/helpCenter/private/detail/deleteComment"})
	public String helpCenter_detail_deleteComment(HttpServletRequest req, HelpCenterCommentDTO helpCenterCommentDTO) {
		memberService.deleteHelpCenterComment(helpCenterCommentDTO.getIdentification_code());
		
		return "redirect:/member/helpCenter/" + getHelpCenterType(req) + "/detail?article_code=" + helpCenterCommentDTO.getArticle_code(); 
	}
	
	@RequestMapping(value = {"/member/helpCenter/public/detail/delete", "/member/helpCenter/private/detail/delete"})
	public String helpCenter_detail_delete(HttpServletRequest req, @RequestParam("article_code")String article_code) {
		memberService.deleteHelpCenter(article_code);
		
		String contextRoot = new HttpServletRequestWrapper(req).getRealPath("/");
		String fileRoot = contextRoot + "resources" + File.separator + "summernote" + File.separator + "uploadImages" + File.separator + article_code;
		FileUtils.deleteQuietly(new File(fileRoot));
		
		return "redirect:/member/helpCenter/" + getHelpCenterType(req);
	}
}