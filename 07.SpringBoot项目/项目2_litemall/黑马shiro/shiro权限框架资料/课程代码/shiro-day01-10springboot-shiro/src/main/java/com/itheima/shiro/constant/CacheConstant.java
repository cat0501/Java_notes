
package com.itheima.shiro.constant;





/**
 * @Description 缓存键值常量类
 */
public class CacheConstant extends SuperConstant{

	public final static String GROUP_CAS="group_shiro:";
	
	public static final String TOKEN = GROUP_CAS+"token:";
	
	public static final String ROLE_KEY =GROUP_CAS+"role_key:";
	
	public static final String RESOURCES_KEY =GROUP_CAS+"resources_key:";
	
	public static final String RESOURCES_KEY_IDS =GROUP_CAS+"resources_key_ids:";
	
	public final static String FIND_USER_BY_LOGINNAME =GROUP_CAS+"findUserByLoginName";
	
	public final static String FIND_ROLE_BY_USERID =GROUP_CAS+"findRoleByUserId";
	
	public final static String FIND_RESOURCE_BY_USERID =GROUP_CAS+"findResourceByUserId";
	
	public final static String FIND_LENDER_BY_LOGINNAME =GROUP_CAS+"findLenderByLoginName";
	
	public final static String FIND_ROLE_BY_LENDERID =GROUP_CAS+"findRoleByLenderId";
	
	public final static String FIND_RESOURCE_BY_LENDERID =GROUP_CAS+"findResourceByLenderId";

	public final static String FIND_CUSTOMER_BY_LOGINNAME =GROUP_CAS+"findCustomerByLoginName";
	
	public final static String FIND_ROLE_BY_CUSTOMERID =GROUP_CAS+"findRoleByCustomerId";
	
	public final static String FIND_RESOURCE_BY_CUSTOMERID =GROUP_CAS+"findResourceByCustomerId";

	public final static String CANCEL_CUSTOMER =GROUP_CAS+"cancelCustomer:";

}
