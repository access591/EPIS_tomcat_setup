
package  com.epis.utilities;
import java.text.DecimalFormat;
public class CurrencyFormat {

	public String getCurrency(double num)
		{
		DecimalFormat df=new DecimalFormat("00");
		DecimalFormat df3=new DecimalFormat("000");
		String str="0";
		String sign="";
		if(num<0)
		{
			sign="-";
			num=Math.abs(num);
		}

		if(num>=0 && num<=999){
			str=String.valueOf((int)num);
		}else
		{
		 str = String.valueOf(num);  
        long b = (long) (num%1000); 
		String bb=df3.format(b);
		//System.out.println(str);
		if(str.indexOf('E')!=-1){		
			if(str.substring(str.indexOf('.')+1,str.indexOf('E')).length()>=Integer.parseInt(str.substring(str.indexOf('E')+1)))
			{
				str = bb+"."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)));
				str=str.substring(0,str.length()-1);
			}
			else	
				str = bb;				
		}
        else 
		{
			str=bb+"."+str.substring(str.indexOf('.')+1) ;   
			str=str.substring(0,str.indexOf('.'));
		}
	    long a = (long)num/1000;
		int i=1;
	    while(a!=0) {	
			if(i<=3){
					b = (long)a%100;
					a = (long)a/100;
				
					if(b<=9&&a==0)
						str = b+","+str;
					else
					str = df.format(b)+","+str;         
			}else{				
				str=a+str;
				a=0;
			}
			i++;            
        }
		}
		return (sign+str);

        }
	
	/*public String getDecimalCurrency(double num)
		{
		DecimalFormat df=new DecimalFormat("00");
		DecimalFormat df1=new DecimalFormat("0.00");
		DecimalFormat df3=new DecimalFormat("000");
		String str="0";
		String sign="";
		if(num<0)
		{
			sign="-";
			num=Math.abs(num);
		}

		if(num>=0 && num<=999)
		{
			str=df1.format(num);

		}
		else
		{
		 str = String.valueOf(num);  
        long b = (long) (num%1000); 
		String bb=df3.format(b);
		if(str.indexOf('E')!=-1)
		{		
			if(str.substring(str.indexOf('.')+1,str.indexOf('E')).length()>=Integer.parseInt(str.substring(str.indexOf('E')+1)))
			{
				//String decimals=df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)))));
				//str = bb+"."+decimals.substring(2,decimals.length());
				String decimals="";
				if(Double.parseDouble("0."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1))))*1000 <995)
					 decimals=df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)))));
				else
				{
					decimals="0.00";
					bb=String.valueOf(Integer.parseInt(bb)+1);
				}				
				str = bb+"."+decimals.substring(2,decimals.length());
			}
			else	
				str = bb+".00";				
		}
        else 
		{
			if(Double.parseDouble(str.substring(str.indexOf('.')+1))/1000 <995)
			{
				str=bb+"."+df1.format(num).substring(str.indexOf('.')+1);   
			}
			else
			{
				bb=String.valueOf(Integer.parseInt(bb)+1);
				str = bb+".00";
			}
		}
	    long a = (long)num/1000;
		int i=1;
	    while(a!=0) 	
		{	
			if(i<=3)
			{
					b = (long)a%100;
					a = (long)a/100;
				
					if(b<=9&&a==0)
						str = b+","+str;
					else
					str = df.format(b)+","+str;         
			}
			else
			{				
				//System.out.println(".....a..."+a);
				str=a+str;
				a=0;
			}
			i++;            
        }
		}
		
		return (sign+str);

        }*/

public String getDecimalCurrency(double num)
{
	DecimalFormat df=new DecimalFormat("00");
	DecimalFormat df1=new DecimalFormat("0.00");
	DecimalFormat df3=new DecimalFormat("000");
	String str="0";
	String sign="";
	if(num<0)
	{
		sign="-";
		num=Math.abs(num);
	}

	if(num>=0 && num<=999)
	{
		if((String.valueOf(num).indexOf("."))==-1)
		{
			str=String.valueOf((int)num);
			str=str+".00";
		}
		else
		{
			str=df1.format(num);
		}
	}
	else
	{
		str = String.valueOf(num);  
		long b = (long) (num%1000); 
		String bb=df3.format(b);
		//System.out.println(str);
		if(str.indexOf('E')!=-1)
		{		
			if(str.substring(str.indexOf('.')+1,str.indexOf('E')).length()>=Integer.parseInt(str.substring(str.indexOf('E')+1)))
			{
				//str = bb+"."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)));
				String decimals="";
				if(Double.parseDouble("0."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1))))*1000 <995)
					decimals=df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)))));
				else
				{
					decimals="0.00";
					bb=String.valueOf(df3.format(Integer.parseInt(bb)+1));
				}				
				str = bb+"."+decimals.substring(2,decimals.length());
				//str=str.substring(0,str.length()-1);
			}
			else	
				str = bb+".00";				
		}
		else
		{
			//System.out.println("..asasas...."+df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1))));
			if(Double.parseDouble("0."+str.substring(str.indexOf('.')+1))*1000>=995)
			{
				bb=df3.format(Integer.parseInt(bb)+1);
				//System.out.println("......."+bb);
				str=bb+".00";
			}
			else
				str=bb+"."+df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1))).substring(2,4) ;   
			//System.out.println("..............."+str);
			//str=str.substring(0,str.indexOf('.'));
		}
		long a = (long)num/1000;
		int i=1;
		while(a!=0) 
		{	
			if(i<=3)
			{
				b = (long)a%100;
				a = (long)a/100;
				if(b<=9&&a==0)
					str = b+","+str;
				else
					str = df.format(b)+","+str;         
			}
			else
			{				
				//System.out.println(".....a..."+a);
				str=a+str;
				a=0;
			}
			i++;            
		}
	}
	return (sign+str);
}
}
