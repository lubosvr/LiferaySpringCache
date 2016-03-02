package cz.lubosvr.liferay.spring.cache;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.io.Serializable;

/**
 * Implementation of the Spring Cache interface to use intern Liferay caching mechanism
 * Keys must be Serializable
 * User: LubosVrba
 * Date: 29.2.2016
 */
public class LiferayCache implements Cache {

    private static final Object NULL_HOLDER = new NullHolder();
    private final PortalCache portalCache;

    /**
     * Constructor to create cache by name
     * @param name - name of the cache in Liferay
     * @param multiVM - should the cache be shared between multiple VM (MultiVMPool or SingleVMPool is used)
     * @param blocking - should the cache be blocking
     */
    public LiferayCache(String name, boolean multiVM, boolean blocking) {
        if (multiVM) {
            portalCache = MultiVMPoolUtil.getCache(name, blocking);
        } else {
            portalCache = SingleVMPoolUtil.getCache(name, blocking);
        }
    }

    /**
     * Creates cache interface based on delivered Liferay Cache
     * @param portalCache
     */
    public LiferayCache(PortalCache portalCache) {
        this.portalCache = portalCache;
    }

    @Override
    public String getName() {
        return portalCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return portalCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = this.portalCache.get((Serializable) key);
        return (value != null ? new SimpleValueWrapper(fromStoreValue(value)) : null);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object value = fromStoreValue(this.portalCache.get((Serializable) key));
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }

    @Override
    public void put(Object key, Object value) {
        this.portalCache.put((Serializable) key, toStoreValue(value));
    }

    @Override
    public void evict(Object key) {
        this.portalCache.remove((Serializable) key);

    }

    @Override
    public void clear() {
        this.portalCache.removeAll();
    }


    protected Object fromStoreValue(Object storeValue) {
        if (storeValue == NULL_HOLDER) {
            return null;
        }
        return storeValue;
    }
    protected Object toStoreValue(Object userValue) {
        if (userValue == null) {
            return NULL_HOLDER;
        }
        return userValue;
    }
    private static class NullHolder implements Serializable {
    }
}
