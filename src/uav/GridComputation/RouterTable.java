package uav.GridComputation;

import java.net.InetAddress;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

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

    <T> boolean setNetwork_destination(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        network_destination = str_address;
        return true;
    }

    String getNetwork_destination() {
        return network_destination;
    }

    <T> boolean setNetmask(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        netmask = str_address;
        return true;
    }

    String getNetmask() {
        return netmask;
    }

    <T> boolean setGateway(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        gateway = str_address;
        return true;
    }

    String getGateway() {
        return gateway;
    }

    <T> boolean setRinterface(T address) {
        String str_address = convert_toString(address);
        if (str_address == null) return false;
        rinterface = str_address;
        return true;
    }

    String getRinterface() {
        return rinterface;
    }

    void setMetric(int metric_) {
        metric = metric_;
    }

    Integer getMetric() {
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

public class RouterTable<T> {
    ConcurrentSkipListMap<String, T> routes;
}
