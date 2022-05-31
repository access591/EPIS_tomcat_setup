package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;

public class Form8Bean implements Serializable{
Form8RemittanceBean remittanceBean=new Form8RemittanceBean();
ArrayList Form8List=new ArrayList();
public ArrayList getForm8List() {
	return Form8List;
}
public void setForm8List(ArrayList form8List) {
	Form8List = form8List;
}
public Form8RemittanceBean getRemittanceBean() {
	return remittanceBean;
}
public void setRemittanceBean(Form8RemittanceBean remittanceBean) {
	this.remittanceBean = remittanceBean;
}

}
