package marshalling;


public class Order implements java.io.Serializable {
	
	private int id;
	
	private String prodName;
	
	private String creater;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Override
	public String toString() {
		return "{'id': '" + this.id + "','prodName':'" + this.prodName
				+ "','creater':'" + this.creater + "'}";
	}
	
}	
