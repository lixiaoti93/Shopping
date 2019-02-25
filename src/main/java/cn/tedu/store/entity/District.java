package cn.tedu.store.entity;

import java.io.Serializable;

public class District implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -678527142013877169L;
	private Integer id;
	private String parent;
	private String code;
	private String name;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getParent() {
		return parent;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "District [id=" + id + ", parent=" + parent + ", code=" + code + ", name=" + name + "]";
	}

}
