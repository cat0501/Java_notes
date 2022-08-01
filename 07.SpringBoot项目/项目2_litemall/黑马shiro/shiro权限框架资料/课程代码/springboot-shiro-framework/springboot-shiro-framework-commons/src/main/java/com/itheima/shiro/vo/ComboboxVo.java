package com.itheima.shiro.vo;

import com.itheima.shiro.utils.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 		复选框
 */
@Getter
@Setter
public class ComboboxVo extends ToString {
	
	/**传递值**/
	private String id;
	
	/**显示值**/
	private String text;
	
	/**是否选择**/
	private Boolean selected;

	public ComboboxVo() {
		super();
	}

	public ComboboxVo(String id, String text) {
		this.id = id;
		this.text = text;
	}
	

}
