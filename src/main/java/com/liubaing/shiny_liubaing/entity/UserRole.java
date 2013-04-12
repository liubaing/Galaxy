package com.liubaing.shiny_liubaing.entity;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 用户课程关联POJO
 * @author heshuai
 * @see <b>MySQL table</b>:sec_userrole_tab
 */
@Entity
@Table(name = "sec_userrole_tab")
@SuppressWarnings("unused")
public class UserRole extends BaseModel{
	
	private static final int ROLE_TEA = 8, ROLE_STU = 9;
	
	public static final int TYPE_ORG = 1, TYPE_COURSE = 3; 

	private static final long serialVersionUID = 8891039473353145443L;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userid")
	private User user;			//用户ID
	
	@EmbeddedId
	private UserRolePK id;
	
	public UserRolePK getId() {
		return id;
	}
	
	public void setId(UserRolePK id) {
		this.id = id;
	}


	/**
	 * 
	  * 方法描述：判断用户角色是否为学生
	  * @return true 学生 
	 */
	public boolean isStu(){
		if (this.id.getType() == TYPE_ORG && this.id.getRoleID() == ROLE_STU) {
			return true;
		}
		return false;
	}
}
