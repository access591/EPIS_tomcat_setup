package com.epis.utilities;

public class ApplicationActionException  extends UtilityException {
  

        /**
         * 
         */
        private static final long serialVersionUID = 4831134605470360849L;

        /**
         * @param code
         * @param msg
         */
        public ApplicationActionException(String code, String msg) {
            super(code, msg);
        }

        /**
         * @param code
         * @param msg
         * @param cause
         */
        public ApplicationActionException(String code, String msg, Throwable cause) {
            super(code, msg, cause);
        }

    }


