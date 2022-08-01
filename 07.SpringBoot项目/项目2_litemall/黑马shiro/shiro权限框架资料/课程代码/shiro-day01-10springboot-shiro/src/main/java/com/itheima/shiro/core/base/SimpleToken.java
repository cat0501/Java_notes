
package com.itheima.shiro.core.base;

import org.apache.shiro.authc.UsernamePasswordToken;


/**
 * @Description 自定义tooken
 */
public class SimpleToken extends UsernamePasswordToken {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -4849823851197352099L;

	private String tokenType;
	
	private String quickPassword;

	/**
	 * Constructor for SimpleToken
	 * @param tokenType
	 */
	public SimpleToken(String tokenType, String username,String password) {
		super(username,password);
		this.tokenType = tokenType;
	}
	
	public SimpleToken(String tokenType, String username,String password,String quickPassword) {
		super(username,password);
		this.tokenType = tokenType;
		this.quickPassword = quickPassword;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getQuickPassword() {
		return quickPassword;
	}

	public void setQuickPassword(String quickPassword) {
		this.quickPassword = quickPassword;
	}
	
	
}
