package kemasanku;

import jakarta.mvc.security.Csrf;
import jakarta.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;

@ApplicationPath("")
public class Aplikasi extends ResourceConfig {
	
	public Aplikasi(){
		property(Csrf.CSRF_PROTECTION, Csrf.CsrfOptions.EXPLICIT);
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}
}