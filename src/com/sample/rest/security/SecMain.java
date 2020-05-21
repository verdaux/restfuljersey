/**
 * 
 */
package com.sample.rest.security;


import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;

/**
 * @author Pragnesh
 *
 */
public class SecMain
{
	public static void main(String[] args)
	{
		AesKey key = new AesKey(ByteUtil.randomBytes(16));
		 JsonWebEncryption jwe = new JsonWebEncryption();
		 jwe.setPayload("Hello World!");
		 jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
		 jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		 jwe.setKey(key);
		 String serializedJwe = "";
		try
		{
			serializedJwe = jwe.getCompactSerialization();
		} catch (JoseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("Serialized Encrypted JWE: " + serializedJwe);
		 jwe = new JsonWebEncryption();
		 jwe.setAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.WHITELIST, 
		        KeyManagementAlgorithmIdentifiers.A128KW));
		 jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.WHITELIST, 
		        ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
		 jwe.setKey(key);
		 try
		{
			jwe.setCompactSerialization(serializedJwe);
		} catch (JoseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try
		{
			System.out.println("Payload: " + jwe.getPayload());
		} catch (JoseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
