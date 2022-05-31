package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class NomineeForm2Info implements Serializable{
	  ArrayList personalList=new ArrayList();
	   ArrayList nomineeList=new ArrayList();
	   ArrayList familyList=new ArrayList();
	public ArrayList getFamilyList() {
		return familyList;
	}
	public void setFamilyList(ArrayList familyList) {
		this.familyList = familyList;
	}
	public ArrayList getNomineeList() {
		return nomineeList;
	}
	public void setNomineeList(ArrayList nomineeList) {
		this.nomineeList = nomineeList;
	}
	public ArrayList getPersonalList() {
		return personalList;
	}
	public void setPersonalList(ArrayList personalList) {
		this.personalList = personalList;
	}
	   

}
