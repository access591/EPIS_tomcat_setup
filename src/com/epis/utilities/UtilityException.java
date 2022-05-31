package com.epis.utilities;



import java.io.PrintWriter;
import java.io.StringWriter;


public abstract class UtilityException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 3695288342030798587L;
    private final String mCode;
    

    /**
     * @param String code
     * @param String msg
     */
    protected UtilityException(String code, String msg) {
        super(msg);
        mCode = code;
    }

    /**
     * @param String code
     * @param String msg
     * @param Throwable cause
     */
    protected UtilityException(String code, String msg, Throwable cause) {
        super(msg, cause);
        mCode = code;
    }

    /**
     * @return error Code
     */
    public final String getCode() {
        return mCode;
    }

    /** * Returns the exceptions stack trace in a string. */
    public final String getStackTraceString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        printStackTrace(pw);
        return sw.toString();
    }
}
