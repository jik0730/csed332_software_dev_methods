package edu.ncsu.csc.itrust.enums;

public enum TransactionLogColumnType {
	/** Table TransactionLog 's column 'transactionID' */
	TRANSACTION_ID(0, "transactionID"),
	/** Table TransactionLog 's column 'loggedinMID' */
	LOGGED_IN_MID(1, "loggedinMID"),
	/** Table TransactionLog 's column 'secondaryMID' */
	SECONDARY_MID(2, "secondaryMID"),
	/** Table TransactionLog 's column 'transactionCode' */
	TRANSACTION_CODE(3, "transactionCode"),
	/** Table TransactionLog 's column 'timeLogged' */
	TIME_LOGGED(4, "timeLogged"),
	/** Table TransactionLog 's column 'addedInfo' */
	ADDED_INFO(5, "addedInfo");
	private int code;
	private String name;
	private TransactionLogColumnType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	/**
	 * get code
	 * @param code code
	 * @return code
	 */
	public int getCode(){
		return this.code;
	}
	/**
	 * get name
	 * @param code code
	 * @return name
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * parse
	 * @param code code
	 * @return type
	 */
	public static TransactionLogColumnType parse(int code) {
		for (TransactionLogColumnType type : TransactionLogColumnType.values()) {
			if (type.code == code)
				return type;
		}
		throw new IllegalArgumentException("No transaction log column type exists for code " + code);
	}
}
