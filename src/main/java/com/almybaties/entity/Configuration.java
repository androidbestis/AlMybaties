package com.almybaties.entity;

import com.almybaties.mapping.AlMappedStatement;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * xml 配置信息
 */
@Data
public class Configuration {

      private String alenvironmentsId;
      private ConcurrentHashMap<String, String> jdbcConnectInfo;
      private String mapperResource;
      private HashMap<String, String> mapperInterface;
      private HashMap<String, MapperEntity> mappedmap;

      protected final Map<String, AlMappedStatement> mappedStatements = new StrictMap<AlMappedStatement>("Mapped Statements collection");





      public AlMappedStatement getMappedStatement(String id) {
            return this.getMappedStatement(id,true);
      }

      public AlMappedStatement getMappedStatement(String id, boolean validateIncompleteStatements) {
            if (validateIncompleteStatements) {
                  //TODO validate statement`s complete
                  //buildAllStatements();
            }
            return mappedStatements.get(id);
      }

      //User-defined Map which extends HashMap
      private static class StrictMap<V> extends HashMap<String,V> {
            private static final long serialVersionUID = -4950446264854982944L;
            private final String name;

            public StrictMap(String name, int initialCapacity, float loadFactor) {
                  super(initialCapacity, loadFactor);
                  this.name = name;
            }

            public StrictMap(String name, int initialCapacity) {
                  super(initialCapacity);
                  this.name = name;
            }

            public StrictMap(String name) {
                  super();
                  this.name = name;
            }

            public StrictMap(String name, Map<String, ? extends V> m) {
                  super(m);
                  this.name = name;
            }

            @SuppressWarnings("unchecked")
            public V put(String key, V value) {
                  if (containsKey(key)) {
                        throw new IllegalArgumentException(name + " already contains value for " + key);
                  }
                  if (key.contains(".")) {
                        final String shortKey = getShortName(key);
                        if (super.get(shortKey) == null) {
                              super.put(shortKey, value);
                        } else {
                              super.put(shortKey, (V) new Ambiguity(shortKey));
                        }
                  }
                  return super.put(key, value);
            }

            public V get(Object key) {
                  V value = super.get(key);
                  if (value == null) {
                        throw new IllegalArgumentException(name + " does not contain value for " + key);
                  }
                  if (value instanceof Ambiguity) {
                        throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
                                + " (try using the full name including the namespace, or rename one of the entries)");
                  }
                  return value;
            }

            private String getShortName(String key) {
                  final String[] keyParts = key.split("\\.");
                  return keyParts[keyParts.length - 1];
            }

            protected static class Ambiguity {
                  final private String subject;

                  public Ambiguity(String subject) {
                        this.subject = subject;
                  }

                  public String getSubject() {
                        return subject;
                  }
            }

      }
}
