package com.mycompany.kushopsystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleStore {

    private static final Path FILE = Paths.get("sales.txt");

    public static List<String[]> load() {
        List<String[]> list = new ArrayList<>();
        if (!Files.exists(FILE)) {
            return list;
        }
        try {
            for (String line : Files.readAllLines(FILE)) {
                String[] p = line.split("\\|", -1);
                if (p.length == 6) {
                    list.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void add(String username, Product product, int qty) throws IOException {
        double total = product.getPrice() * qty;
        String line = LocalDate.now() + "|" + username + "|" + product.getProductId() + "|"
                + product.getProductName() + "|" + qty + "|" + total + System.lineSeparator();
        Files.writeString(FILE, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}