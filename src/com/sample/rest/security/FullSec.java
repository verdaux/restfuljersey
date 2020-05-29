/**
 * 
 */
package com.sample.rest.security;

import java.util.Arrays;
import java.util.List;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.EcJwkGenerator;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.EllipticCurves;
import org.jose4j.lang.JoseException;

/**
 * @author Pragnesh
 *
 */
public class FullSec
{

	public static void main(String[] args)
	{
		//Security.addProvider(new BouncyCastleProvider());
		
		// Generate an EC key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
	    EllipticCurveJsonWebKey senderJwk = null;
		try
		{
			senderJwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);
		} catch (JoseException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

	    // Give the JWK a Key ID (kid), which is just the polite thing to do
	    senderJwk.setKeyId("sender's key");


	    // Generate an EC key pair, wrapped in a JWK, which will be used for encryption and decryption of the JWT
	    EllipticCurveJsonWebKey receiverJwk=null;
		try
		{
			receiverJwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);
		} catch (JoseException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

	    // Give the JWK a Key ID (kid), which is just the polite thing to do
	    receiverJwk.setKeyId("receiver's key");
	    
	    String randomJSON = "{\r\n" + 
	    		"  \"kty\": \"RSA\",\r\n" + 
	    		"  \"e\": \"AQAB\",\r\n" + 
	    		"  \"kid\": \"ipru\",\r\n" + 
	    		"  \"n\": \"gQMP8xtzxFoJ6ZIPtxSqyT6I3xsitWtx_bdXY4JvJhuvLJ7Y_zVmTg649nIZm7H9zz8k4zRVwIL3TRoDHcDiSizSmkGkjtYJ282OwjqI4TOnVTRfClTJVKO0qkWNAMWR8r\r\n" + 
	    		"F9hZlQ-M57CwMYKiYZ05nclfNZSyaGM_y1Da7yrZhqGM4LVyifLJJKn5asZcq4by1W5N-zo-hv63AUr_7mtdvbsolhh75lkDHsSQXaQZDsXOFq5JdmLOt_vEo6rvq4ksKiAtZ7JGrD\r\n" + 
	    		"o4YjkNyfgcHDZmgkz_8tshOuM3dZizs4yjEkKUTwjg_on77zQmrgUh1249bQ8kRBKNcSqAPgmw\"\r\n" + 
	    		"}";
	    
	    // Create the Claims, which will be the content of the JWT
	    JwtClaims claims = new JwtClaims();
	    claims.setIssuer("sender");  // who creates the token and signs it
	    claims.setAudience("receiver"); // to whom the token is intended to be sent
	    claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
	    claims.setGeneratedJwtId(); // a unique identifier for the token
	    claims.setIssuedAtToNow();  // when the token was issued/created (now)
	    claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
	    claims.setSubject("subject"); // the subject/principal is whom the token is about
	    claims.setClaim("email","mail@example.com"); // additional claims/attributes about the subject can be added
	    claims.setStringClaim("jsonVal", randomJSON);
	    List<String> groups = Arrays.asList("group-1", "other-group", "group-3");
	    claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array

	    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
	    // In this example it is a JWS nested inside a JWE
	    // So we first create a JsonWebSignature object.
	    JsonWebSignature jws = new JsonWebSignature();

	    // The payload of the JWS is JSON content of the JWT Claims
	    jws.setPayload(claims.toJson());

	    // The JWT is signed using the sender's private key
	    jws.setKey(senderJwk.getPrivateKey());

	    // Set the Key ID (kid) header because it's just the polite thing to do.
	    // We only have one signing key in this example but a using a Key ID helps
	    // facilitate a smooth key rollover process
	    jws.setKeyIdHeaderValue(senderJwk.getKeyId());

	    // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

	    // Sign the JWS and produce the compact serialization, which will be the inner JWT/JWS
	    // representation, which is a string consisting of three dot ('.') separated
	    // base64url-encoded parts in the form Header.Payload.Signature
	    String innerJwt="";
		try
		{
			innerJwt = jws.getCompactSerialization();
		} catch (JoseException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	    // The outer JWT is a JWE
	    JsonWebEncryption jwe = new JsonWebEncryption();

	    // The output of the ECDH-ES key agreement will encrypt a randomly generated content encryption key
	    jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.ECDH_ES_A128KW);

	    // The content encryption key is used to encrypt the payload
	    // with a composite AES-CBC / HMAC SHA2 encryption algorithm
	    String encAlg = ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256;
	    jwe.setEncryptionMethodHeaderParameter(encAlg);

	    // We encrypt to the receiver using their public key
	    jwe.setKey(receiverJwk.getPublicKey());
	    jwe.setKeyIdHeaderValue(receiverJwk.getKeyId());

	    // A nested JWT requires that the cty (Content Type) header be set to "JWT" in the outer JWT
	    jwe.setContentTypeHeaderValue("JWT");

	    // The inner JWT is the payload of the outer JWT
	    jwe.setPayload(innerJwt);

	    // Produce the JWE compact serialization, which is the complete JWT/JWE representation,
	    // which is a string consisting of five dot ('.') separated
	    // base64url-encoded parts in the form Header.EncryptedKey.IV.Ciphertext.AuthenticationTag
	    String jwt = "";
		try
		{
			jwt = jwe.getCompactSerialization();
		} catch (JoseException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	    // Now you can do something with the JWT. Like send it to some other party
	    // over the clouds and through the interwebs.
	    System.out.println("JWT: " + jwt);


	    // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
	    // be used to validate and process the JWT.
	    // The specific validation requirements for a JWT are context dependent, however,
	    // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
	    // and audience that identifies your system as the intended recipient.
	    // It is also typically good to allow only the expected algorithm(s) in the given context
	    AlgorithmConstraints jwsAlgConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
	            AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

	    AlgorithmConstraints jweAlgConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
	            KeyManagementAlgorithmIdentifiers.ECDH_ES_A128KW);

	    AlgorithmConstraints jweEncConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST,
	            ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);

	    JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	            .setRequireExpirationTime() // the JWT must have an expiration time
	            .setMaxFutureValidityInMinutes(300) // but the  expiration time can't be too crazy
	            .setRequireSubject() // the JWT must have a subject claim
	            .setExpectedIssuer("sender") // whom the JWT needs to have been issued by
	            .setExpectedAudience("receiver") // to whom the JWT is intended for
	            .setDecryptionKey(receiverJwk.getPrivateKey()) // decrypt with the receiver's private key
	            .setVerificationKey(senderJwk.getPublicKey()) // verify the signature with the sender's public key
	            .setJwsAlgorithmConstraints(jwsAlgConstraints) // limits the acceptable signature algorithm(s)
	            .setJweAlgorithmConstraints(jweAlgConstraints) // limits acceptable encryption key establishment algorithm(s)
	            .setJweContentEncryptionAlgorithmConstraints(jweEncConstraints) // limits acceptable content encryption algorithm(s)
	            .build(); // create the JwtConsumer instance

	    try
	    {
	        //  Validate the JWT and process it to the Claims
	        JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
	        System.out.println("JWT validation succeeded! " + jwtClaims);
	        System.out.println("Payload is::  "+ jwtClaims.getClaimValue("jsonVal").toString());
	    }
	    catch (InvalidJwtException e)
	    {
	        // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
	        // Hopefully with meaningful explanations(s) about what went wrong.
	        System.out.println("Invalid JWT! " + e);
	    }
	}

}
