package com.epis.bean;

import java.util.ArrayList;
import java.util.List;


public class MailBean {
private String fromAddress="",toRecipient="",mailSubject="";
private  boolean htmlBodyContent=false,plainBodyContent=false,attachement=false;
private MailContent content;
private List ccRecipient=new ArrayList();
private List fileAttachedList=new ArrayList();

public List getFileAttachedList() {
	return fileAttachedList;
}

public void setFileAttachedList(List fileAttachedList) {
	this.fileAttachedList = fileAttachedList;
}

public List getCcRecipient() {
	return ccRecipient;
}

public void setCcRecipient(List ccRecipient) {
	this.ccRecipient = ccRecipient;
}



public boolean isAttachement() {
	return attachement;
}

public void setAttachement(boolean attachement) {
	this.attachement = attachement;
}

public boolean isHtmlBodyContent() {
	return htmlBodyContent;
}

public void setHtmlBodyContent(boolean htmlBodyContent) {
	this.htmlBodyContent = htmlBodyContent;
}

public boolean isPlainBodyContent() {
	return plainBodyContent;
}

public void setPlainBodyContent(boolean plainBodyContent) {
	this.plainBodyContent = plainBodyContent;
}



public String getFromAddress() {
	return fromAddress;
}

public void setFromAddress(String fromAddress) {
	this.fromAddress = fromAddress;
}



public String getMailSubject() {
	return mailSubject;
}

public void setMailSubject(String mailSubject) {
	this.mailSubject = mailSubject;
}

public String getToRecipient() {
	return toRecipient;
}

public void setToRecipient(String toRecipient) {
	this.toRecipient = toRecipient;
}

public MailContent getContent() {
	return content;
}

public void setContent(MailContent content) {
	this.content = content;
}

}
