package br.com.testezup.models.errors;

public class DefaultResponseError {

	private String status;
	private String type;
	private String message;
	
	public DefaultResponseError(Exception ex){
		this.status = "error";
		this.type = ex.getClass().toString();
		this.message = ex.getMessage().toString();
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
