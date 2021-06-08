package id.altanovela.conf;

import java.util.Arrays;

import org.apache.catalina.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AbstractSSMConfiguration;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.ExtendedSSMCacheManager;
import com.google.code.ssm.spring.SSMCache;

import de.javakaffee.web.msm.MemcachedBackupSessionManager;

@Configuration
@EnableCaching
public class WebMemcacheConfig extends AbstractSSMConfiguration {

    @Value("${cache.address.memcache}")
    String cacheAddress;

    @Bean
    @Override
    public CacheFactory defaultMemcachedClient() {
        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName("defaultCache");
        cacheFactory.setInitializeTranscoders(false);
        cacheFactory.setCacheClientFactory(new MemcacheClientFactoryImpl());
        cacheFactory.setAddressProvider(new DefaultAddressProvider(cacheAddress));
        CacheConfiguration cacheConfig = new CacheConfiguration();
        cacheConfig.setConsistentHashing(true);
        cacheFactory.setConfiguration(cacheConfig);
        return cacheFactory;
    }

    @Bean
    public CacheManager cacheManager() throws Exception {
        ExtendedSSMCacheManager cacheManager = new ExtendedSSMCacheManager();
        Cache cache = this.defaultMemcachedClient().getObject();
        cacheManager.setCaches(Arrays.asList(new SSMCache(cache, 300, false)));
        return cacheManager;
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                MemcachedBackupSessionManager manager = new MemcachedBackupSessionManager();
                manager.setMemcachedNodes("n1:" + cacheAddress);
                manager.setRequestUriIgnorePattern(".*\\.(ico|png|gif|jpg|css|js)$");
                manager.setSticky(true);
                manager.setSessionBackupAsync(true);
                manager.setSessionBackupTimeout(1000);
                context.setManager(manager);
            }
        };
    }
}
