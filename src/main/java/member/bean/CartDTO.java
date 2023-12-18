package member.bean;

public class CartDTO {
	private String id;
	private String item;
	private String price;
	private int count;
	
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