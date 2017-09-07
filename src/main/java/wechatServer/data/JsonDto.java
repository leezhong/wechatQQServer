package wechatServer.data;

import java.io.Serializable;

/**
 * 
 * @author yansong.wang
 *
 */
public class JsonDto implements Serializable{

	private Integer code;

	private String msg;

	private Object data;

	public JsonDto() {
		this.code = JsonDtoStatus.OK;
		this.msg = JsonDtoStatus.OK_INFO;
	}

	public JsonDto(Integer code, String msg) {
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

	public Object getData() {
		return data;
	}

//	public void clearData() {
//		this.data.;
//	}

	public void setData(Object data) {
		this.data = data;
	}

//	public void addData(Map<String, Object> dataMap) {
//		for (Map.Entry<String, Object> data : dataMap.entrySet()) {
//			this.data.put(data.getKey(), data.getValue());
//		}
//	}
//
//	public void addData(String key, Object value) {
//		this.data.put(key, value);
//	}
}
