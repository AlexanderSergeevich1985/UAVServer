package uav.GridComputation;

import java.net.InetAddress;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Collectors;

/**
 * Created by administrator on 29.05.18.
 */
class RouteDesc {
    static <T> String convert_toString(T address) {
        String str_address = null;
        try {
            if (address.getClass() == String.class) {
                str_address = (String) address;
            } else if (address instanceof InetAddress) {
                str_address = ((InetAddress) address).getHostAddress();
            } else return null;
        } catch (ClassCastException ex) {
            System.out.println(ex);
            return null;
        }
        return str_address;
    }

    public <T> boolean setNetwork_destination(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        network_destination = str_address;
        return true;
    }

    public String getNetwork_destination() {
        return network_destination;
    }

    public <T> boolean setNetmask(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        netmask = str_address;
        return true;
    }

    public String getNetmask() {
        return netmask;
    }

    public <T> boolean setGateway(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        gateway = str_address;
        return true;
    }

    public String getGateway() {
        return gateway;
    }

    public <T> boolean setRinterface(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        rinterface = str_address;
        return true;
    }

    public String getRinterface() {
        return rinterface;
    }

    public void setMetric(int metric_) {
        metric = metric_;
    }

    public Integer getMetric() {
        return metric;
    }

    String network_destination;
    String netmask;
    String gateway;
    String rinterface;
    Integer metric;
}

class SharedRouteDesc {
    public RouteDesc getItem() {
        return item;
    }

    public void setItem(RouteDesc item_) {
        updater.compareAndSet(this, this.item, item_);
    }

    private volatile RouteDesc item;
    private static final AtomicReferenceFieldUpdater<SharedRouteDesc, RouteDesc> updater =
            AtomicReferenceFieldUpdater.newUpdater(SharedRouteDesc.class, RouteDesc.class, "item");
}


public class RouterTable {
    ConcurrentSkipListMap<String, RouteDesc> routes;

    public void addRoute(String key, RouteDesc desc) {
        routes.put(key, desc);
    }
    public RouteDesc getDescByKey(String key) {
        return routes.get(key);
    }
    public List<RouteDesc> findDescByMetric(int first_metric, Optional<Integer> optParam) {
        List<RouteDesc> RoutDescs = null;
        if(!optParam.isPresent()) {
            RoutDescs = routes.values().parallelStream()
                    .filter((item) -> {
                        return item.getMetric() == first_metric ? true : false;
                    })
                    .collect(Collectors.toList());
        }
        else {
            Integer second_metric = optParam.get();
            RoutDescs = routes.values().parallelStream()
                    .filter((item) -> {
                        Integer metric = item.getMetric();
                        return (metric >= first_metric && metric <= second_metric) ? true : false;
                    })
                    .collect(Collectors.toList());
        }
        return RoutDescs;
    }
}
