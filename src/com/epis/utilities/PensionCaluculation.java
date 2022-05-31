package com.epis.utilities;

import com.epis.utilities.Log;
import java.text.*;
public  class  PensionCaluculation
{
	private static Log log = new Log(DBUtility.class);
	public static String getYield(double price,double coupon,double parValue,double years)
	{	
		String Yield="-";
		try{
		DecimalFormat df = new DecimalFormat("#.###");
		coupon = (coupon)/100;
		double currentYield = coupon*parValue*100/price;
		Yield=df.format(currentYield);
	}catch(Exception exp){
		log.error("pensionCaluculation:Exception:getYield");
		log.error("pensionCaluculation:Error:"+exp.getMessage());
		log.info("pensionCaluculation:Error:"+exp.getMessage());
	}
	return(Yield);
	}
	public static String getYTM(double price,double coupon,double parValue,double years)
	{
		
		String YTM="-";
		try{
			if(coupon!=0)
			{
		DecimalFormat df = new DecimalFormat("#.##");
		coupon = (coupon)/100;
		double c = coupon*parValue;
		double E = .00001;
		double ytm=0;
		for (int k = 0; k < 100; k++)
		{
			double fYTM=(c + parValue)*Math.pow(coupon,years+1) - parValue*Math.pow(coupon,years) - (c+price)*coupon + price;
			double dfYTM=(years+1)*(c + parValue)*Math.pow(coupon,years) - years*parValue*Math.pow(coupon,years-1) - (c+price);
		if (Math.abs(fYTM) < E) break;
		
		while (Math.abs(dfYTM) < E) 
			coupon+= .1;
		coupon = coupon-(((c + parValue)*Math.pow(coupon,years+1) - parValue*Math.pow(coupon,years) - (c+price)*coupon + price)/((years+1)*(c + parValue)*Math.pow(coupon,years) - years*parValue*Math.pow(coupon,years-1) - (c+price)));
		
		}
		
		if (Math.abs((c + parValue)*Math.pow(coupon,years+1) - parValue*Math.pow(coupon,years) - (c+price)*coupon + price) >= E) ytm= -1;  // error
	  else
		  ytm=((1/coupon) - 1)*100;
		YTM=df.format(ytm);
		}
		else
		{
			YTM="0";
		}
	}catch(Exception exp){
		log.error("pensionCaluculation:Exception:getYTM");
		log.error("pensionCaluculation:Error:"+exp.getMessage());
		log.info("pensionCaluculation:Error:"+exp.getMessage());
	}
		
	return(YTM);
	}
	public static String getYTMAnnualized(double ytm){
		double ytmannualized=0;
		System.out.println(ytm);
		DecimalFormat df = new DecimalFormat("#.##");
		String ytmannual="";
		ytmannualized=ytm+((ytm/2)*(ytm/2)/100.0);
		ytmannual=df.format(ytmannualized);
		return ytmannual;
		
	}
	
	
	/*public static void main(String args[]){
		double years=10;
		double par=100.1;
		
		double coupon=8.32;
		System.out.println(getYield(par,coupon,100,10));
		System.out.println(getYTM(par,coupon,100,10));
		System.out.println(getYTMAnnualized(Double.parseDouble(getYTM(par,coupon,100,10))));
		
	}*/
}
