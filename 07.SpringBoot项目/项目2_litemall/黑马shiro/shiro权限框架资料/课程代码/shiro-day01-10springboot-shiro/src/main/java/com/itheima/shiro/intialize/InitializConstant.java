
package com.itheima.shiro.intialize;

import com.itheima.shiro.constant.ResourceConstant;
import com.itheima.shiro.constant.RoleConstant;
import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.core.adapter.UserAdapter;
import com.itheima.shiro.mapper.UserMapper;
import com.itheima.shiro.pojo.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.lang.reflect.Field;
import java.util.List;


@Component
@Log4j2
public class InitializConstant implements ServletContextAware {
	
	private ServletContext servletContext;
	
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserAdapter userAdapter;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@PostConstruct
	public void onApplicationEvent() {
		loadConstant(SuperConstant.class);
		loadConstant(ResourceConstant.class);
		loadConstant(RoleConstant.class);
		log.info("-------------------------初始化权限开始---直接先预加载数据5分钟");
		List<User> list =userMapper.selectByExample(null);
		for (User user : list) {
			userAdapter.findResourceByUserId(user.getId());
			userAdapter.findRoleByUserId(user.getId());
			userAdapter.findUserByLoginName(user.getLoginName());
		}
		log.info("-------------------------初始化权限结束---直接先预加载数据5分钟");
	}
	
	
	/**
	 * 
	 * <b>方法名：</b>：loadConstant<br>
	 * <b>功能说明：</b>：加载常量类<br>
	 * @author <font color='blue'>束文奇</font> 
	 * @date  2017-8-1 下午1:54:00
	 * @param classTag
	 */
	private void loadConstant(Class<?> classTag){
		log.info("-------------------------初始化{}开始",classTag.getName());
		Field fields[] = classTag.getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			Object object = null;
			try {
				object = field.get(classTag);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			servletContext.setAttribute(classTag.getSimpleName()+"_"+ fieldName, object);
			log.info(classTag.getSimpleName()+":---key：{}，---value：{}",classTag.getSimpleName()+"_" + fieldName,object);
		}
		log.info("------------------------初始化{}结束",classTag.getName());
	}
	

}
