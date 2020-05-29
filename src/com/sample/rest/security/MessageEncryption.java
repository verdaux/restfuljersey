/**
 * 
 */
package com.sample.rest.security;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

/**
 * @author Pragnesh
 *
 */
public class MessageEncryption
{
	public static void main(String[] args)
	{
		MessageEncryption me = new MessageEncryption();
		
		me.messageEncryptor();
	}
	public String messageEncryptor()
	{
		//
	    // An example showing the use of JSON Web Encryption (JWE) to encrypt and then decrypt some content
	    // using a symmetric key and direct encryption.
	    //

	    // The content to be encrypted
	    String message = "Well, as of this moment, they're on DOUBLE SECRET PROBATION!";

	    // The shared secret or shared symmetric key represented as a octet sequence JSON Web Key (JWK)
	    //String jwkJson = "{\"kty\":\"oct\",\"k\":\"Fdh9u8rINxfivbrianbbVT1u232VQBZYKx1HGAGPt2I\"}";
		/*
		 * String jwkJson = "{\r\n" +
		 * "  \"p\": \"-YHhQSHxQAFPi8d8uCN5NDCaBZ27rgyhj3I9t-zk7Vk\",\r\n" +
		 * "  \"kty\": \"RSA\",\r\n" +
		 * "  \"q\": \"8v8RrQRiAQD9vnbQOhJlxAlNnmehO9XZagKbGYmrwUs\",\r\n" +
		 * "  \"d\": \"ZrS-UaF1ciicGzL59Mvt2UA5EsmRRimjPx2edPP5VlYcAnWCpHiub7fezdmxzLa4fx9A35Vb7ttogoAxA9BRMQ\",\r\n"
		 * + "  \"e\": \"AQAB\",\r\n" + "  \"kid\": \"ipru\",\r\n" +
		 * "  \"qi\": \"ki4dF5p0wdVK3oPOpBbrdf4XgIXCLc1Q82DCzO4NUHk\",\r\n" +
		 * "  \"dp\": \"YcZF0VJNyfk5hNstwrMUAiGxmdXQAEqu3Rm1mefZZjE\",\r\n" +
		 * "  \"dq\": \"rceWx4qL4DXqQg1Mi0te9hzFItTv7Q_Z5cOpUlEvwRM\",\r\n" +
		 * "  \"n\": \"7NVgiTL29kysb7hGeNeylTMaHyKrjY9UbvF3mMLC2fbUbpZl3gHs0fiI7GsnZNlw42Ljpd5KubdBY48bKXSiEw\"\r\n"
		 * + "}";
		 */
		/*
		 * String jwkJson = "{\r\n" +
		 * "  \"p\": \"xKA4Mjyg_F6IiVJQ4L_F4l1taKPUuRYkEyhUd3jQLiOuDFC9fnMoYEyf8RvV8QmiGc8n_bnfoVqxikxSsNgcLCdUuZyf_rMQKGNQD-KfaUNiWTUqo_u3hjkov0-L1QQmeM\r\n"
		 * + "uPkBcrvfoDWlnmkQFuMWYwpa5zVlZr75g2mgnmqnM\",\r\n" +
		 * "  \"kty\": \"RSA\",\r\n" +
		 * "  \"q\": \"p_gU7sCMMjMXfYaJFq73o66IRDjptLL-4otsaULbuumNJJxRdaOffhqCyDmiD_3bj8md3QAxWioJEVTW86FVgtdsvE3rpAMV5Ed2pgpypcLOX_XswkuRjbBNWtbFOZpyOA\r\n"
		 * + "L4QFhRCtDstyWtJbXqAUbmImfgoLHmVUWpAFHsHzk\",\r\n" +
		 * "  \"d\": \"fvh4lhcKlNDYuJwcA1-_xlhZdNrMCik5Ay-SPkl9AtB0U0Hy5xmzGle7CjG5qlY_Kd1HfKVhUBqVMbjMIFhZXg9HJrI8NqfJBB8GxXm-hYXExkLI1u4jR73mbT4OTBgmT-\r\n"
		 * +
		 * "kHVJlT68urpWbiPXlKKctgIeRimWfM5RVtS45HITldPT-jZfPlG1ocAlhu8kZ2tNdv7Jhym6GHa45vX6LeKzwVVk3rsCWxWGOxDaspg9K4_zDxbijkh6u2lEw6CW5ZkuSkdTQUVlIi\r\n"
		 * +
		 * "kiY0zeLq1_IjU8VgAETYk20i7N4nF0XO94vq7wliuNN5chubdnRnmec92t1RgsG6QD3Wk4YswQ\",\r\n"
		 * + "  \"e\": \"AQAB\",\r\n" + "  \"kid\": \"ipru\",\r\n" +
		 * "  \"qi\": \"wGDyrugJoh5VgXKgkXf7Q3rFkwL5YAtv3waIaaDWBigL1mnBEYWhkd7meWG-wQRcirzVMv49mbc-SXEqGhg_lHw4EX5_aT8zUY3yw7IV-AhHi0-WKPlRqIaBEFM7y1Ey6\r\n"
		 * + "t78xTEUmsZvhKAPGksLhvZZyMdCDPYzZjCZ-Jbiok0\",\r\n" +
		 * "  \"dp\": \"Un9KtME9ZkWfkcYoyvOLajt60SGT-ggxIJyWO_5DkmhD-UTTDL_yW90qajYcoGJDUgLZq5oWGxP7U6RMOYii4V-rK6lsc6fbiqaIi_GworC0DevBtUXCrZ1D3GjaqYhxP\r\n"
		 * + "9Ngq7sdDjtctsbrd0GbYzyWAp3cJIhkBwXdOuoWjsM\",\r\n" +
		 * "  \"dq\": \"jb6ro4M3jBFTjGlg_JRwhF9_vNquVgALWhBAT1_NX6FBq5iCoZyPtnIW1XLVUVtv0ppYrqw_-Y-pkUegEX6fU8gJiarT5Nd5vOeVt67vavTHBXHZ92igDwqtbK_7RY4RP\r\n"
		 * + "zU6W8prsY33hOK1iDJIpAU5peU1SUZ3UpRzdsKrWPk\",\r\n" +
		 * "  \"n\": \"gQMP8xtzxFoJ6ZIPtxSqyT6I3xsitWtx_bdXY4JvJhuvLJ7Y_zVmTg649nIZm7H9zz8k4zRVwIL3TRoDHcDiSizSmkGkjtYJ282OwjqI4TOnVTRfClTJVKO0qkWNAMWR8r\r\n"
		 * +
		 * "F9hZlQ-M57CwMYKiYZ05nclfNZSyaGM_y1Da7yrZhqGM4LVyifLJJKn5asZcq4by1W5N-zo-hv63AUr_7mtdvbsolhh75lkDHsSQXaQZDsXOFq5JdmLOt_vEo6rvq4ksKiAtZ7JGrD\r\n"
		 * +
		 * "o4YjkNyfgcHDZmgkz_8tshOuM3dZizs4yjEkKUTwjg_on77zQmrgUh1249bQ8kRBKNcSqAPgmw\"\r\n"
		 * + "}"; JsonWebKey jwk = null; try { jwk = JsonWebKey.Factory.newJwk(jwkJson);
		 * } catch (JoseException e2) { // TODO Auto-generated catch block
		 * e2.printStackTrace(); }
		 */
		
	    // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
	    RsaJsonWebKey rsaJsonWebKey=null;
		try
		{
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	    // Give the JWK a Key ID (kid), which is just the polite thing to do
	    rsaJsonWebKey.setKeyId("ipru");
		
	    
	    
	    // Create a new Json Web Encryption object
	    JsonWebEncryption senderJwe = new JsonWebEncryption();

	    // The plaintext of the JWE is the message that we want to encrypt.
	    senderJwe.setPlaintext(message);

	    // Set the "alg" header, which indicates the key management mode for this JWE.
	    // In this example we are using the direct key management mode, which means
	    // the given key will be used directly as the content encryption key.
	    senderJwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.RSA_OAEP);

	    // Set the "enc" header, which indicates the content encryption algorithm to be used.
	    // This example is using AES_128_CBC_HMAC_SHA_256 which is a composition of AES CBC
	    // and HMAC SHA2 that provides authenticated encryption.
	    senderJwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);

	    // Set the key on the JWE. In this case, using direct mode, the key will used directly as
	    // the content encryption key. AES_128_CBC_HMAC_SHA_256, which is being used to encrypt the
	    // content requires a 256 bit key.
	    //senderJwe.setKey(jwk.getKey());
	    
	 // The JWT is signed using the private key
	    senderJwe.setKey(rsaJsonWebKey.getRsaPublicKey());

	    // Set the Key ID (kid) header because it's just the polite thing to do.
	    // We only have one key in this example but a using a Key ID helps
	    // facilitate a smooth key rollover process
	    senderJwe.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

	    // Produce the JWE compact serialization, which is where the actual encryption is done.
	    // The JWE compact serialization consists of five base64url encoded parts
	    // combined with a dot ('.') character in the general format of
	    // <header>.<encrypted key>.<initialization vector>.<ciphertext>.<authentication tag>
	    // Direct encryption doesn't use an encrypted key so that field will be an empty string
	    // in this case.
	    String compactSerialization =
		null;
		try
		{
			compactSerialization = senderJwe.getCompactSerialization();
		} catch (JoseException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    // Do something with the JWE. Like send it to some other party over the clouds
	    // and through the interwebs.
	    System.out.println("JWE compact serialization: " + compactSerialization);

	    // That other party, the receiver, can then use JsonWebEncryption to decrypt the message.
	    JsonWebEncryption receiverJwe = new JsonWebEncryption();

	    // Set the algorithm constraints based on what is agreed upon or expected from the sender
	    AlgorithmConstraints algConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST, KeyManagementAlgorithmIdentifiers.RSA_OAEP);
	    receiverJwe.setAlgorithmConstraints(algConstraints);
	    AlgorithmConstraints encConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST, ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
	    receiverJwe.setContentEncryptionAlgorithmConstraints(encConstraints);

	    // Set the compact serialization on new Json Web Encryption object
	    try
		{
			receiverJwe.setCompactSerialization(compactSerialization);
		} catch (JoseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Symmetric encryption, like we are doing here, requires that both parties have the same key.
	    // The key will have had to have been securely exchanged out-of-band somehow.
	    //receiverJwe.setKey(jwk.getKey());
	    String pubKey = "{\r\n" + 
	    		"  \"kty\": \"RSA\",\r\n" + 
	    		"  \"e\": \"AQAB\",\r\n" + 
	    		"  \"kid\": \"ipru\",\r\n" + 
	    		"  \"n\": \"gQMP8xtzxFoJ6ZIPtxSqyT6I3xsitWtx_bdXY4JvJhuvLJ7Y_zVmTg649nIZm7H9zz8k4zRVwIL3TRoDHcDiSizSmkGkjtYJ282OwjqI4TOnVTRfClTJVKO0qkWNAMWR8r\r\n" + 
	    		"F9hZlQ-M57CwMYKiYZ05nclfNZSyaGM_y1Da7yrZhqGM4LVyifLJJKn5asZcq4by1W5N-zo-hv63AUr_7mtdvbsolhh75lkDHsSQXaQZDsXOFq5JdmLOt_vEo6rvq4ksKiAtZ7JGrD\r\n" + 
	    		"o4YjkNyfgcHDZmgkz_8tshOuM3dZizs4yjEkKUTwjg_on77zQmrgUh1249bQ8kRBKNcSqAPgmw\"\r\n" + 
	    		"}";
	    PublicJsonWebKey pubKeyVal=null;
	    try
		{
			 pubKeyVal = PublicJsonWebKey.Factory.newPublicJwk(pubKey);
		} catch (JoseException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    receiverJwe.setKey(rsaJsonWebKey.getRsaPrivateKey());

	    // Get the message that was encrypted in the JWE. This step performs the actual decryption steps.
	    String plaintext =
		null;
		try
		{
			plaintext = receiverJwe.getPlaintextString();
		} catch (JoseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // And do whatever you need to do with the clear text message.
	    System.out.println("plaintext: " + plaintext);
	    
	    return plaintext;
	}

}
