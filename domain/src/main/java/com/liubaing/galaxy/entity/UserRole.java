package com.liubaing.galaxy.entity;

import javax.persistence.*;


/**
 * 用户角色POJO
 * @author heshuai
 * @see <b>MySQL table</b>:sec_userrole_tab
 */
@Entity
@Table(name = "sec_userrole_tab")
@SuppressWarnings("unused")
public class UserRole extends BaseModel{
	
	private static final long serialVersionUID = 8891039473353145443L;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userid")
	private User user;			//用户ID
	
	@EmbeddedId
	private UserRolePK id;
	
	public UserRolePK getId() {
		return id;
	}

    @Override
    public String toString() {
        return super.toString();
    }

    public void setId(UserRolePK id) {
		this.id = id;
	}
}
