package model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Money implements Parcelable {

	private long money_id;
	private long category_id;
	private double amount;
	private String notes;
	private Date date;
	private String rule;
	private String type;

	public Money( long category_id, double amount, String notes,
			Date date, String rule, String type) {
		super();

		this.category_id = category_id;
		this.amount = amount;
		this.notes = notes;
		this.date = date;
		this.rule = rule;
		this.type = type;
	}

	
	public Money(){
		super();
	}




	public long getMoney_id() {
		return money_id;
	}

	public void setMoney_id(long money_id) {
		this.money_id = money_id;
	}

	public long getCategory() {
		return category_id;
	}

	public void setCategory(long category_id) {
		this.category_id = category_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	//Parcelable part

	public Money(Parcel in){

		this.category_id = in.readLong();
		this.amount = in.readDouble();
		this.notes = in.readString();
		this.date = (Date) in.readSerializable();
		this.rule = in.readString();
		this.type = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(this.category_id);
		dest.writeDouble(this.amount);
		dest.writeString(this.notes);
		dest.writeSerializable(this.date);
		dest.writeString(this.rule);
		dest.writeString(this.type);

	}
	public static final Parcelable.Creator<Money> CREATOR = new Parcelable.Creator<Money>() {
		public Money createFromParcel(Parcel in) {
			return new Money(in);
		}
		public Money[] newArray(int size){
			return new Money[size];
		}

};
}
