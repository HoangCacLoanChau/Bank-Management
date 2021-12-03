package enity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class Log implements Serializable {
	private String accountId;
	private Date date;
	private String function;
	private int month;
	

	public Log(String accountId) {
		this.accountId = accountId;
	}

	public Log() {
	}

	public int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int current = cal.get(Calendar.MONTH) + 1;
		return current == 12 ? 0 : current;
	}

	public int getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -30);
		int current = cal.get(Calendar.MONTH) + 1;
		return current == 12 ? 0 : current;
	}

	public Log(String accountId, String function) {
		this.accountId = accountId;
		this.date = new Date();
		this.function = function;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public void print() {
		System.out.println("|" + date + "|" + accountId + "|" + function);
	}
}
