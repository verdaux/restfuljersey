package com.sample.rest.auth;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.glassfish.jersey.server.ContainerRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                            .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {

            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void validateToken(String token) throws Exception
    {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    	
    	try
		{
    		validate(token);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
    	
    	
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
}