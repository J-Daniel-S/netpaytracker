package the.best.nettracker.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import the.best.nettracker.documents.User;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "Uf7JX6UjLUKlGaYRhV5z5EWVqSlh/UQmXCHJ9HbDzKjtnMYFaOJc0C1jfufA1Xq7V2nrLSeQ+d72qwHJj+x0pzQ6O8DEUxYezPpHEuXQkICj+jCiQesYvKfIScMibB5eC5fAdnooT305m/aLCG277xbVQwKnumPCFFra3E/kZhZWQ5pj4Ltvyyfp/+lDQ73g+UbqYyJuaCQ91EcBkpERP762LtXoV43JFHVsuqR6SA8tqMbb2dzxX7+RDEpAH1odPawdIrJvsp54lHofMTUxrua2CoWAs/d69IK8UfIPMYQ40MPI5Jgd4c7UB/pTZtxayOiazs2JveAPjK7eJ7BmOk/oSE6DI9zWFl5euIPela0=";
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(User user) {
		return generateToken(new HashMap<>(), user);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails user) {
		final String username = extractUsername(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	
	
}
