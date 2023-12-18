package member.bean;

import java.util.List;

public class PaymentCompleteDTO {
	private String order_code;
	private List<CartDTO> cart_list;
	private String payment_price;
	
	public String getOrder_code() {
		return order_code;
	}
	
	public List<CartDTO> getCart_list() {
		return cart_list;
	}
	
	public String getPayment_price() {
		return payment_price;
	}
	
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	
	public void setCart_list(List<CartDTO> cart_list) {
		this.cart_list = cart_list;
	}
	
	public void setPayment_price(String payment_price) {
		this.payment_price = payment_price;
	}
}