package com.example.jxiexcel.save2excel;

import org.json.JSONObject;

import java.io.Serializable;

public class Order implements Serializable {

	public String id;
	public String lockRomId;
	public String lockAddr;

	
	public Order(String id, String lockRomId, String lockAddr) {
		this.id = id;
		this.lockRomId = lockRomId;
		this.lockAddr = lockAddr;
	}

	public Order(JSONObject obj) {
		this.id = obj.optString("order_number");
		this.lockRomId = obj.optString("lockRomId");
		this.lockAddr = obj.optString("lockAddr");
	}
}
