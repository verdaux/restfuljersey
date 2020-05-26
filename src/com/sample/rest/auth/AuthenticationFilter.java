package com.sample.rest.auth;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.glassfish.jersey.server.ContainerRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter
{
	@Context
	private HttpServletRequest request;
	@Context
	private SecurityContext securityContext;
	@Context
	private UriInfo uriInfo;
	
    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME1 = "Bearer";
    private static final String AUTHENTICATION_SCHEME2 = "Basic";
    private static final String reasonForException = "Bad request";

    public void filter(ContainerRequestContext requestContext) throws IOException
    {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        final SecurityContext securityContext = (SecurityContext) requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext()
		{
			
			@Override
			public boolean isUserInRole(String role)
			{
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isSecure()
			{
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public Principal getUserPrincipal()
			{
				// TODO Auto-generated method stub
				return () -> "user";
			}
			
			@Override
			public String getAuthenticationScheme()
			{
				// TODO Auto-generated method stub
				return null;
			}
		});
        
        
        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                            .substring(AUTHENTICATION_SCHEME1.length()).trim();

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
                    .startsWith(AUTHENTICATION_SCHEME1.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                AUTHENTICATION_SCHEME1 + " realm=\"" + REALM + "\"")
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