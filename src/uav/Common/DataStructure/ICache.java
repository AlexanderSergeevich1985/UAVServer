package uav.Common.DataStructure;

import java.io.Serializable;

public interface ICache<T, ID extends Serializable> {
    long DEDAULT_UPDATE_PERIOD = 100;

    boolean add(ID key, Object value); //periodInMillis

    boolean remove(ID key);

    T get(ID key);

    boolean clear();

    long size();
}
