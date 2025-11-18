
package utils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    // Very simple serialization-based persistence
    public static void save(String path, Map<?, ?> accounts, List<?> transactions) throws IOException {
        Map<String, Object> store = new HashMap<>();
        store.put("accounts", accounts);
        store.put("transactions", transactions);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(store);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            return (Map<String, Object>) obj;
        }
    }
}
