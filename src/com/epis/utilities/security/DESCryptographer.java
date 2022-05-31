 /**
  * File       : DESCryptographer.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/*import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;*/

import java.util.Base64;


import com.epis.utilities.Log;
import com.epis.utilities.security.EncryptionException;
import com.epis.utilities.security.Cryptographer;
import com.epis.utilities.security.PropertyHelper;


/**
 * @author nagarjunreddyt
 *
 */
public class DESCryptographer extends Cryptographer {
	static Log log = new Log(DESCryptographer.class);

	public String doDecrypt(String input) throws EncryptionException{
		String result = null;
		try {

			Cipher decCipher = Cipher.getInstance("DES");

			SecretKeyFactory desKeyFactory = SecretKeyFactory
					.getInstance("DES");
			DESKeySpec spec = new DESKeySpec(generateKeySpc(PropertyHelper
					.getProperty("DES.Key")), 0);
			decCipher.init(Cipher.DECRYPT_MODE, desKeyFactory
					.generateSecret(spec));

			//BASE64Decoder b64Dec = new BASE64Decoder();
			//byte[] raw = b64Dec.decodeBuffer(input);
			
			byte[] raw = Base64.getDecoder().decode(input);
			byte[] numberBytes = decCipher.doFinal(raw);
			result = new String(numberBytes, "UTF8");
		}catch (NoSuchAlgorithmException _NSAEx) {
		    log.printStackTrace(_NSAEx);
            throw new EncryptionException("9002", "Exception while decrypting");
        }catch (NoSuchPaddingException _NSPEx) {
            log.printStackTrace(_NSPEx);
            throw new EncryptionException("9002","Exception while decrypting");
        }catch (InvalidKeyException _InKEx) {
            log.printStackTrace(_InKEx);
            throw new EncryptionException("9002", "Exception while decrypting");
        }catch (InvalidKeySpecException _InKSEx) {
            log.printStackTrace(_InKSEx);
            throw new EncryptionException("9002", "Exception while decrypting");
        }catch (IOException _IOEx) {
            log.printStackTrace(_IOEx);
            throw new EncryptionException("9002", "Exception while decrypting");
        }catch (BadPaddingException _BPEx) {
            log.printStackTrace(_BPEx);
            throw new EncryptionException("9002", "Exception while decrypting");
        }catch (IllegalBlockSizeException _IBSEx) {
            log.printStackTrace(_IBSEx);
            throw new EncryptionException("9002", "Exception while decrypting");
		} catch (Exception e) {
		    log.printStackTrace(e);
            throw new EncryptionException("1002", "Unknown exception while decrypting");
		}
		return result;
	}

	public String doEncrypt(String input)throws EncryptionException{
		String encS = null;
		try {
			Cipher encCipher = Cipher.getInstance("DES");

			SecretKeyFactory desKeyFactory = SecretKeyFactory
					.getInstance("DES");
			DESKeySpec spec = new DESKeySpec(generateKeySpc(PropertyHelper
					.getProperty("DES.Key")), 0);

			encCipher.init(Cipher.ENCRYPT_MODE, desKeyFactory
					.generateSecret(spec));

			byte[] raw = encCipher.doFinal(input.getBytes("UTF8"));
			//BASE64Encoder b64Enc = new BASE64Encoder();
			//encS = b64Enc.encode(raw);
			
			encS = Base64.getEncoder().encodeToString(raw);
		}catch (NoSuchAlgorithmException _NSAEx) {
            log.printStackTrace(_NSAEx);
            throw new EncryptionException("9001", "Exception while encrypting");
        }catch (NoSuchPaddingException _NSPEx) {
            log.printStackTrace(_NSPEx);
            throw new EncryptionException("9001","Exception while encrypting");
        }catch (InvalidKeyException _InKEx) {
            log.printStackTrace(_InKEx);
            throw new EncryptionException("9001", "Exception while encrypting");
        }catch (InvalidKeySpecException _InKSEx) {
            log.printStackTrace(_InKSEx);
            throw new EncryptionException("9001", "Exception while encrypting");
        }catch (IOException _IOEx) {
            log.printStackTrace(_IOEx);
            throw new EncryptionException("9001","Exception while encrypting");
        }catch (BadPaddingException _BPEx) {
            log.printStackTrace(_BPEx);
            throw new EncryptionException("9001","Exception while encrypting");
        }catch (IllegalBlockSizeException _IBSEx) {
            log.printStackTrace(_IBSEx);
            throw new EncryptionException("9001","Exception while encrypting");
        }catch (EncryptionException _EnEx) {
            log.printStackTrace(_EnEx);
            throw new EncryptionException("9001","Exception while encrypting");
        }catch (Exception e) {
            log.printStackTrace(e);
            throw new EncryptionException("1001", " Unknown exception while encrypting");
        }
		return encS;
	}

	public static void main(String args[]) {
	    try {
	        DESCryptographer desCry = new DESCryptographer();
	        String str = desCry.doEncrypt("dale");
	        System.out.println("encrypt "+str);
	        String str1 = desCry.doDecrypt(str);
	        System.out.println("decrypt "+str1);
        } catch (Exception e) {
            // TODO: handle exception
        }
	}
		
}
