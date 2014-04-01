package com.liubaing.galaxy.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liubaing.galaxy.entity.User;
import com.liubaing.galaxy.entity.UserRole;
import com.liubaing.galaxy.oauth.TokenGenerator;
import com.liubaing.galaxy.oauth.TokenStorage;
import com.liubaing.galaxy.repository.jpa.UserRepository;
import com.liubaing.galaxy.util.core.Const;
import com.liubaing.galaxy.util.core.JSONUtils;


/**
 * 类说明:用户业务
 * @author heshuai
 * @version Feb 24, 2012
 *
 */
@Service 
public class UserManager
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenGenerator tokenGenerator;
	
	@Autowired 
	private TokenStorage tokenStorage;
	
	/**
	 * 
	  * 方法描述：通过用户名和密码获取用户
	  * @author heshuai
	  * @version 2012-3-13 上午11:13:12
	 */
	public String login(String userStr)
	{
		StringBuffer result = new StringBuffer("");
		User user = JSONUtils.fromJson(userStr, User.class);
		if(user != null){
			User temp = userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
			if (temp != null) {
				//Generate Token By userID&password
				String token = tokenGenerator.generateValue(temp.getId()+temp.getPassword());
				//Persist Token
				tokenStorage.persist(token, temp.getId());
				if (!token.equals(temp.getToken())) {
					temp.setToken(token);
					userRepository.save(temp);
				}
				result.append("{\"status\":\"true\",\"access_token\":\""+token+"\",\"userID\":");
				result.append(temp.getId()+",\"userName\":\""+temp.getName()+"\"}");
			}else {
				result.append(Const.ERROR_MSG_NULL);
			}
		} else {
			result.append(Const.ERROR_MSG_NULL);
		}
		return result.toString();
	}
	/**
	 * 
	  * 方法描述：根据token查找用户
	  * @author heshuai
	  * @version 2012-5-8 下午02:56:36
	 */
	public boolean isUserExistByToken(String token)
	{
		return userRepository.findByToken(token) != null ? true : false;
	}
	
	/**
	 * 
	  * 方法描述：判断用户是否是学生
	  * @param userID 用户ID 
	  * @return true 学生
	 */
	public boolean isStu(int userID){
		User user = userRepository.findOne(userID);
		Set<UserRole> userRoles = user.getUserRoles();
		boolean _role = false;
		for (Iterator<UserRole> iterator = userRoles.iterator(); iterator.hasNext();) {
			UserRole userRole = (UserRole) iterator.next();
			if(userRole.isStu()){
				_role = true;
				break; 
			}
		}
		return _role;
	}
	/**
	 * 
	  * 方法描述：获取用户域
	  * @param userID 用户ID 
	  * @return 域ID
	 */
	public int getAspIDByUserID(int userID){
		User user = (User) userRepository.findOne(userID);
		int aspID = 0;
		if (user != null) {
			aspID = user.getOrganization().getAspID();
		}
		return aspID;
	}
}
