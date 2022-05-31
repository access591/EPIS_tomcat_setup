package com.epis.action.advances;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts.action.ActionForward;

public class ForwardParameters
{
private Map params = null;

public ForwardParameters()
{
params = new HashMap();
}

/**
* Add a single parameter and value.
*
* @param paramName     Parameter name
* @param paramValue    Parameter value
*
* @return A reference to this object.
*/
public ForwardParameters add(String paramName, String paramValue)
{
params.put(paramName,paramValue);
return this;
}

/**
* Add a set of parameters and values.
*
* @param paramMap  Map containing parameter names and values.
*
* @return A reference to this object.
*/
public ForwardParameters add(Map paramMap)
{
Iterator itr = paramMap.keySet().iterator();
while (itr.hasNext())
{
String paramName = (String) itr.next();
params.put(paramName, paramMap.get(paramName));
}

return this;
}

/**
* Add parameters to a provided ActionForward.
*
* @param forward    The ActionForward object to add parameters to.
*
* @return ActionForward with parameters added to the URL.
*/
public ActionForward forward(ActionForward forward)
{
StringBuffer path = new StringBuffer(forward.getPath());

Iterator itr = params.entrySet().iterator();
if (itr.hasNext())
{
//add first parameter, if avaliable
Map.Entry entry = (Map.Entry) itr.next();
path.append("&" + entry.getKey() + "=" + entry.getValue());

//add other parameters
while (itr.hasNext())
{
entry = (Map.Entry) itr.next();
path.append("&" + entry.getKey() + "=" + entry.getValue());
}
}

System.out.println("--------Path in Forward Parameters---------"+path.toString());
return new ActionForward(path.toString());
}
}
