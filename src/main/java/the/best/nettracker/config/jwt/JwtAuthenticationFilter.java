package the.best.nettracker.config.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.best.nettracker.services.JwtService;
import the.best.nettracker.services.UserService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserService uRepo;
	@Autowired
	private JwtService jServ;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		final String token;
		final String username;
		if (authHeader != null && authHeader.startsWith("Bearer")) {
			token = authHeader.substring(7);
			//this happens in the authController if I'm not mistaken
			username = jServ.extractUsername(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				try {
				UserDetails userDetails = uRepo.findByUsername(username).get();
				if (jServ.isTokenValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
							);
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				} catch (Exception e) {
					//implement another response here
					e.printStackTrace();
					filterChain.doFilter(request, response);
				}
			}
		} 
		
		filterChain.doFilter(request, response);

	}

}
