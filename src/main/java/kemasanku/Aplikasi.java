package kemasanku;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.beanvalidation.MvcBeanValidationFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletProperties;

@ApplicationPath("")
public class Aplikasi extends ResourceConfig {
	
	public Aplikasi(){
		packages("kemasanku");
        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/views");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(MvcBeanValidationFeature.class);
        register(JspMvcFeature.class);
        register(PerSessionComponentProvider.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}
}