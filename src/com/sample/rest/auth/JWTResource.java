package com.sample.rest.auth;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Path("/jwt")
public class JWTResource
{
	//@Context SecurityContext securityContext;
	@Path("/generate-jwt")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String generateJWT(@Context final SecurityContext securityContext)
	{
		
		securityContext.getUserPrincipal();
		String name = "user";
			String generated = generate(name);
		return generated;
	}
	
	@Secured
	@Path("/validate-jwt")
	@POST
	private void validateJWT(String jwt)
	{
		validate(jwt);
	}
	
	private void validate(String hiddenJWT)
	{
		String fixedId = "aGeneratedStringID";
		String clearTextPassword = "this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase";
		byte[] byteBasedPassphrase = DatatypeConverter.parseBase64Binary(clearTextPassword);
		
		Key signatureKey = new SecretKeySpec(byteBasedPassphrase,"HMACSHA256");
		
		JwtParser parser = Jwts.parser();
		
		Claims claims = parser
								.setSigningKey(signatureKey)
								.parseClaimsJws(hiddenJWT)
								.getBody();
		
		System.out.println("subject:: "+claims.getSubject());
		System.out.println("issuer:: "+claims.getIssuer());
	}
	
	private String generate(String userName)
	{ 
		String fixedId = "aGeneratedStringID";
		String clearTextPassword = "this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase-this-is-a-passphrase";
		byte[] byteBasedPassphrase = DatatypeConverter.parseBase64Binary(clearTextPassword);
		
		Key signatureKey = new SecretKeySpec(byteBasedPassphrase,"HMACSHA256");
		
		JwtBuilder builder = Jwts
									.builder()
									.setId(fixedId)
									.setIssuedAt(Date.from(Instant.now()))
									.setSubject(userName)
									.setIssuer("java-ee-webservice")
									.setExpiration(Date.from(Instant.now().plus(10,ChronoUnit.MINUTES)))
									.signWith(signatureKey);
		
		return builder.compact();
	}
}
