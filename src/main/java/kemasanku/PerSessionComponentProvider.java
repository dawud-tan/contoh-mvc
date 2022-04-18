package kemasanku;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.BindingBuilderFactory;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.spi.ComponentProvider;
 
@javax.ws.rs.ext.Provider
public class PerSessionComponentProvider implements ComponentProvider {

    private ServiceLocator locator;

    static class PerSessionFactory implements Factory<BarangController> {
        static ConcurrentHashMap<String, BarangController> perSessionMap
                = new ConcurrentHashMap<String, BarangController>(); 

        private final Provider<HttpServletRequest> requestProvider;
        private final ServiceLocator locator;

        @Inject
        public PerSessionFactory(Provider<HttpServletRequest> request, ServiceLocator locator) {
            this.requestProvider = request;
            this.locator = locator;
        }

        @Override
        @PerLookup
        public BarangController provide() {
            final HttpSession session = requestProvider.get().getSession();
            System.out.println("provide");
            if (session.isNew()) {
                BarangController newInstance = createNewPerSessionBarangController();
                System.out.println("new: "+ session.getId());
                perSessionMap.put(session.getId(), newInstance);
                
                return newInstance;
            } else {
				System.out.println("old: "+session.getId());
                return perSessionMap.get(session.getId());
            }
        }

        @Override
        public void dispose(BarangController r) {
        }

        private BarangController createNewPerSessionBarangController() {
            final BarangController perSessionBarangController = new BarangController();
            locator.inject(perSessionBarangController);
            return perSessionBarangController;
        }
    }

    @Override
    public void initialize(InjectionManager im) {
        this.locator = im.getInstance(ServiceLocator.class);
    }

    @Override
    public boolean bind(Class<?> component, Set<Class<?>> providerContracts) {
        if (component == BarangController.class) {

            final DynamicConfigurationService dynamicConfigService =
                locator.getService(DynamicConfigurationService.class);
            final DynamicConfiguration dynamicConfiguration =
                dynamicConfigService.createDynamicConfiguration();

            BindingBuilderFactory
                .addBinding(BindingBuilderFactory.newFactoryBinder(PerSessionFactory.class)
                .to(BarangController.class), dynamicConfiguration);

            dynamicConfiguration.commit();

            return true;
        }
        return false;
    }

    @Override
    public void done() {
    }
}