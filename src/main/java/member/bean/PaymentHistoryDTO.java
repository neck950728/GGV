package member.bean;

public class PaymentHistoryDTO {
	private String order_code;
	private String id;
	private String item;
	private String price;
	private int count;
	
	public String getOrder_code() {
		return order_code;
	}
	
	public String getId() {
		return id;
	}
	
	public String getItem() {
		return item;
	}
	
	public String getPrice() {
		return price;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}