package me.darragh.eamfhc.manager;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.Collections;
import java.util.List;

/**
 * A simple manager that provides basic functionality for managing a list of objects.
 *
 * @param <T> The type of objects managed by this manager.
 */
public abstract class SimpleManager<T> implements Manager<T> {
    protected final List<T> list = new ObjectArrayList<>();

    @Override
    public void add(T t) {
        this.list.add(t);
    }

    @Override
    public void remove(T t) {
        this.list.remove(t);
    }

    @Override
    public void remove(String id) {
        T t = get(id);
        if (t != null) {
            this.list.remove(t);
        }
    }

    @Override
    public boolean contains(T t) {
        return this.list.contains(t);
    }

    @Override
    public boolean contains(String id) {
        return get(id) != null;
    }

    @Override
    public List<T> getAll() {
        return Collections.unmodifiableList(this.list);
    }

    @Override
    public void clear() {
        this.list.clear();
    }
}
