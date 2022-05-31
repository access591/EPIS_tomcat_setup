/**
 * File       : BottomGridNavigationInfo.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */

package com.epis.bean.rpfc;

import java.io.Serializable;

public class BottomGridNavigationInfo implements  Serializable{
	String statusFirst;
	String statusPrevious;
	String statusNext;
	String statusLast;
	
	String detailFirst;
	String detailPrevious;
	String detailNext;
	String detailLast;
	
	String detailsFirst;
	String detailsPrevious;
	String detailsNext;
	String detailsLast;
	
	String Detail1statusFirst;
	String Detail1statusPrevious;
	String Detail1statusNext;
	String Detail1statusLast;
	public String getDetail1statusFirst() {
		return Detail1statusFirst;
	}
	public void setDetail1statusFirst(String detail1statusFirst) {
		Detail1statusFirst = detail1statusFirst;
	}
	public String getDetail1statusLast() {
		return Detail1statusLast;
	}
	public void setDetail1statusLast(String detail1statusLast) {
		Detail1statusLast = detail1statusLast;
	}
	public String getDetail1statusNext() {
		return Detail1statusNext;
	}
	public void setDetail1statusNext(String detail1statusNext) {
		Detail1statusNext = detail1statusNext;
	}
	public String getDetail1statusPrevious() {
		return Detail1statusPrevious;
	}
	public void setDetail1statusPrevious(String detail1statusPrevious) {
		Detail1statusPrevious = detail1statusPrevious;
	}
	public String getDetailFirst() {
		return detailFirst;
	}
	public void setDetailFirst(String detailFirst) {
		this.detailFirst = detailFirst;
	}
	public String getDetailLast() {
		return detailLast;
	}
	public void setDetailLast(String detailLast) {
		this.detailLast = detailLast;
	}
	public String getDetailNext() {
		return detailNext;
	}
	public void setDetailNext(String detailNext) {
		this.detailNext = detailNext;
	}
	public String getDetailPrevious() {
		return detailPrevious;
	}
	public void setDetailPrevious(String detailPrevious) {
		this.detailPrevious = detailPrevious;
	}
	public String getDetailsFirst() {
		return detailsFirst;
	}
	public void setDetailsFirst(String detailsFirst) {
		this.detailsFirst = detailsFirst;
	}
	public String getDetailsLast() {
		return detailsLast;
	}
	public void setDetailsLast(String detailsLast) {
		this.detailsLast = detailsLast;
	}
	public String getDetailsNext() {
		return detailsNext;
	}
	public void setDetailsNext(String detailsNext) {
		this.detailsNext = detailsNext;
	}
	public String getDetailsPrevious() {
		return detailsPrevious;
	}
	public void setDetailsPrevious(String detailsPrevious) {
		this.detailsPrevious = detailsPrevious;
	}
	public String getStatusFirst() {
		return statusFirst;
	}
	public void setStatusFirst(String statusFirst) {
		this.statusFirst = statusFirst;
	}
	public String getStatusLast() {
		return statusLast;
	}
	public void setStatusLast(String statusLast) {
		this.statusLast = statusLast;
	}
	public String getStatusNext() {
		return statusNext;
	}
	public void setStatusNext(String statusNext) {
		this.statusNext = statusNext;
	}
	public String getStatusPrevious() {
		return statusPrevious;
	}
	public void setStatusPrevious(String statusPrevious) {
		this.statusPrevious = statusPrevious;
	} 
}
