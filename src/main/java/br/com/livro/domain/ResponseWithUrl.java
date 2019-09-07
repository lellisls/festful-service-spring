package br.com.livro.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseWithUrl {
	private String status;
	private String msg;
	private String url;

	public static ResponseWithUrl Ok(String msg, String url) {
		ResponseWithUrl r = new ResponseWithUrl();
		r.status = "OK";
		r.setMsg(msg);
		r.setUrl(url);

		return r;
	}

	public static ResponseWithUrl Error(String msg) {
		ResponseWithUrl r = new ResponseWithUrl();
		r.status = "ERROR";
		r.setMsg(msg);

		return r;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
