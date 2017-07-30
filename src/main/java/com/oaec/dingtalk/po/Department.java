package com.oaec.dingtalk.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Kevin on 2017/7/30.
 */

public class Department {

	private Integer id;

	private boolean createDeptGroup;

	private String name;

	private boolean autoAddUser;

	public Department() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isCreateDeptGroup() {
		return createDeptGroup;
	}

	public void setCreateDeptGroup(boolean createDeptGroup) {
		this.createDeptGroup = createDeptGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAutoAddUser() {
		return autoAddUser;
	}

	public void setAutoAddUser(boolean autoAddUser) {
		this.autoAddUser = autoAddUser;
	}
}
