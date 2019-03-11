package com.wy.entitys;

import javax.annotation.Resource;

import com.wy.bean.factory.annotation.JavaBean;

@JavaBean("aa")
public class User {
	private String name;
	private int id;
	private boolean flag;
	@Resource
	private Money money; 

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String name, int id, boolean flag) {
		super();
		this.name = name;
		this.id = id;
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Money getMoney() {
		return money;
	}

	public void setMoney(Money money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + ", flag=" + flag + ", money=" + money + "]";
	}

}
