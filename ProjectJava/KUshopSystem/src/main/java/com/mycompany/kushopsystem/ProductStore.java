package com.mycompany.kushopsystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProductStore {

    private static final Path FILE = Paths.get("products.txt");

    public static List<Product> load() {
        List<Product> list = new ArrayList<>();
        if (!Files.exists(FILE)) {
            return list;
        }
        try {
            for (String line : Files.readAllLines(FILE)) {
                String[] p = line.split("\\|", -1);
                if (p.length == 8) {
                    list.add(new Product(p[0], p[1], p[2],
                            Integer.parseInt(p[3]), Double.parseDouble(p[4]),
                            p[5], p[6], p[7]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveAll(List<Product> list) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Product p : list) {
            lines.add(String.join("|", p.getProductId(), p.getProductName(), p.getType(),
                    String.valueOf(p.getStock()), String.valueOf(p.getPrice()),
                    p.getStatus(), p.getDate(), p.getImage()));
        }
        Files.write(FILE, lines);
    }
}