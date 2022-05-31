/*
 * AuthenticatorFilter.java
 *
 * Created on April 1, 2009, 12:46 PM
 *
 */

package com.epis.filter;

import com.epis.info.login.LoginInfo;
import com.epis.utilities.Constants;
import com.epis.utilities.Log;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author jayasreek
 */
public class AuthenticatorFilter implements Filter{
    
    Log log = new Log(AuthenticatorFilter.class);
    /** Creates a new instance of AuthorizationFilter */
    public AuthenticatorFilter() {
    }
    
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("AuthenticationFiler : Entering doFilter Method()");
        ActionErrors errors = new ActionErrors();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        LoginInfo user = (LoginInfo) session.getAttribute("user");
        String url = req.getRequestURL().toString();
        //logging
        log.info("AuthenticationFiler : req from host : "+req.getRemoteHost()+" accessed url :"+url);
        boolean skip = false;
        String onErrorUrl="SessionTimeOut.jsp?target=T";
        if(user == null){
            if(url.endsWith("/loginValidation.do")){
                skip = false;
            } else{
                skip = true;
               
                errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("error.authenticator.sessionOut"));
                req.setAttribute(Globals.ERROR_KEY, errors);
            }
        }
        if(skip){
            req.getRequestDispatcher(onErrorUrl).forward(req, res);
        }else{
        	 String screenCode="";
        	 if(request.getParameter("screenCode")!=null)
        		 screenCode=request.getParameter("screenCode");
            // System.out.println("url.."+url);
            // System.out.println("........screenCode...."+screenCode);
             if(!"".equals(screenCode)){
             	session.setAttribute(Constants.CURRENT_ACCESSED_SCREEN_CODE,screenCode);
             }
            chain.doFilter(request, response);
        }
        
    }
 
    public void destroy() {
    }
}

