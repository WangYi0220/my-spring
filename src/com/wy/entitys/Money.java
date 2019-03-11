package com.wy.entitys;

import com.wy.bean.factory.annotation.JavaBean;

@JavaBean
public class Money {
	private int account;
	private double balance;

	public Money() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Money [account=" + account + ", balance=" + balance + "]";
	}

}
