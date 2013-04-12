package com.liubaing.shiny_liubaing.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserRolePK implements Serializable{
	
	private static final long serialVersionUID = -7820596421064349578L;

	
	private int roleID;			//角色ID	 当type=1时，roleID=8是老师，9是学生 
	
	private int relationID;    	//关联属性ID
	
	private int type;			//类型     eg:1(对应机构信息)3(对应课程信息)

	
	
	
	
	/**
	  * 类的构造方法
	  * 创建一个新的实例 UserRolePK.
	  * @param 
	  */
	public UserRolePK() {
		super();
	}

	
	/**
	  * 类的构造方法
	  * 创建一个新的实例 UserRolePK.
	  * @param 
	  * @param roleID
	  * @param relationID
	  * @param type
	  */
	public UserRolePK(int roleID, int relationID, int type) {
		super();
		this.roleID = roleID;
		this.relationID = relationID;
		this.type = type;
	}


	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public int getRelationID() {
		return relationID;
	}

	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + relationID;
		result = prime * result + roleID;
		result = prime * result + type;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRolePK other = (UserRolePK) obj;
		if (relationID != other.relationID)
			return false;
		if (roleID != other.roleID)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
}
