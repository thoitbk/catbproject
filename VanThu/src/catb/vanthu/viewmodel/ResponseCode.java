package catb.vanthu.viewmodel;

import java.io.Serializable;

public class ResponseCode implements Serializable {
	
	private static final long serialVersionUID = -4177396319825907099L;
	private Integer code;
	private String msg;
	
	public ResponseCode() {
		
	}

	public ResponseCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
