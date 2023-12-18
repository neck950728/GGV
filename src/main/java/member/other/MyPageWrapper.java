package member.other;

import java.util.List;

import member.bean.PaymentHistoryDTO;
import member.bean.WatchedMovieDTO;
import movie.bean.ReserveDTO;

public class MyPageWrapper {
	private List<WatchedMovieDTO> watchedMovies;
	private List<ReserveDTO> reserveList;
	private List<PaymentHistoryDTO> paymentList;
	
	public List<WatchedMovieDTO> getWatchedMovies() {
		return watchedMovies;
	}
	
	public List<ReserveDTO> getReserveList() {
		return reserveList;
	}
	
	public List<PaymentHistoryDTO> getPaymentList() {
		return paymentList;
	}
	
	public void setWatchedMovies(List<WatchedMovieDTO> watchedMovies) {
		this.watchedMovies = watchedMovies;
	}
	
	public void setReserveList(List<ReserveDTO> reserveList) {
		this.reserveList = reserveList;
	}
	
	public void setPaymentList(List<PaymentHistoryDTO> paymentList) {
		this.paymentList = paymentList;
	}
}