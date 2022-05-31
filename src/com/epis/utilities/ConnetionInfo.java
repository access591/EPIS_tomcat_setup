package com.epis.utilities;

public class ConnetionInfo {

	private static ThreadLocal local = new ThreadLocal(); 


		public static String getInfo() { 
		System.out.println(local.get()); 

		return (String) local.get(); 
		}



		public static void setInfo(String value) { 

		local.set(value); 
		}



}
