package member.bean;

import java.util.List;

public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private String birth;
	private String gender;
	private String email;
	private String phone;
	private String join_date;
	private String email_auth_key;
	private List<WishListDTO> wishList;
	private List<WishListDTO> watchedMovieList;
	
	public String getId() {
		return id;
	}
	
	public String getPw() {
		return pw;
	}
	
	public String getName() {
		return name;
	}
	
	public String getBirth() {
		return birth;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getJoin_date() {
		return join_date;
	}
	
	public String getEmail_auth_key() {
		return email_auth_key;
	}

	public List<WishListDTO> getWishList() {
		return wishList;
	}
	
	public List<WishListDTO> getWatchedMovieList() {
		return watchedMovieList;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	
	public void setEmail_auth_key(String email_auth_key) {
		this.email_auth_key = email_auth_key;
	}
	
	public void setWishList(List<WishListDTO> wishList) {
		this.wishList = wishList;
	}
	
	public void setWatchedMovieList(List<WishListDTO> watchedMovieList) {
		this.watchedMovieList = watchedMovieList;
	}
}