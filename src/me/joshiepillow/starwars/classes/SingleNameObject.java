package me.joshiepillow.starwars.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SingleNameObject<T> {

    private String name;
    private static Map<Type, List<SingleNameObject>> objects = new HashMap<>();
    private static Map<Type, List<String>> name_list = new HashMap<>();

    /*static SingleNameObject create(String name, Class<?> c) {
        if (isTaken(name, c)) return null;
        return new SingleNameObject(name);
    }*/

    SingleNameObject(String name) {
        this.name = name;
        name_list.computeIfAbsent(getClass(), k -> new ArrayList<>());
        name_list.get(getClass()).add(name);

        objects.computeIfAbsent(getClass(), k -> new ArrayList<>());
        objects.get(getClass()).add(this);
    }

    public String getName() {
        return name;
    }

    boolean setName(String name, Class<?> c) {
        if (isTaken(name, c)) return false;
        name_list.get(c).remove(this.name);
        this.name = name;
        return true;
    }

    static boolean isTaken(String name, Class<?> c) {
        name_list.computeIfAbsent(c, k -> new ArrayList<>());
        return name_list.get(c).contains(name);
    }

    static List<SingleNameObject> getAll(Class c) {
        return objects.get(c);
    }

    static boolean setAll(List<SingleNameObject> data, Class<?> c) {
        for (SingleNameObject datum : data) {
            add(datum.getName(), c);
            add(datum, c);
        }
        return true;
    }

    private static void add(SingleNameObject datum, Class<?> c) {
        objects.computeIfAbsent(c, k -> new ArrayList<>());
        objects.get(c).add(datum);
    }
    private static void add(String name, Class<?> c) {
        name_list.computeIfAbsent(c, k -> new ArrayList<>());
        name_list.get(c).add(name);
    }
    static SingleNameObject getByName(String name, Class<?> c) {
        for (SingleNameObject datum : objects.get(c)) {
            if (datum.name.equals(name)) return datum;
        }
        return null;
    }
}
