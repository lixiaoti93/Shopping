package cn.tedu.store.entity;

public class ResponseResult<T> {
	private Integer status = 200;
	private String message;
	private T data;

	public ResponseResult() {
		super();

	}

	public ResponseResult(Integer status, Exception e) {
		super();
		this.status = status;
		this.message = e.getMessage();
	}
	

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
