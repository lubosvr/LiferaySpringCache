package cz.lubosvr.liferay.spring.cache;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * FactoryBean to create multi VM caches for Liferay internal
 * User: LubosVrba
 * Date: 29.2.2016
 */
public class LiferayMultiVMCacheFactoryBean implements FactoryBean<LiferayCache>, BeanNameAware, InitializingBean {

    private String name;
    private boolean blocking = false;
    private LiferayCache cache;

    public LiferayMultiVMCacheFactoryBean() {
    }

    /**
     * @param name name of the cache in Liferay
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param blocking should the cache be blocking. False by default
     */
    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public void setBeanName(String beanName) {
        if(!StringUtils.hasLength(this.name)) {
            this.setName(beanName);
        }
    }

    public void afterPropertiesSet() {
        this.cache = new LiferayCache(MultiVMPoolUtil.getCache(name, blocking));
    }

    public LiferayCache getObject() {
        return this.cache;
    }

    public Class<?> getObjectType() {
        return LiferayCache.class;
    }

    public boolean isSingleton() {
        return true;
    }

}
