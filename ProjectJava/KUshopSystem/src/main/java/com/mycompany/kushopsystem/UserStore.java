package com.mycompany.kushopsystem;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.*;

public class UserStore {

    private static final Path FILE = Paths.get("users.txt");

    public static String[] find(String username) {
        if (!Files.exists(FILE)) {
            return null;
        }
        try {
            for (String line : Files.readAllLines(FILE)) {
                String[] p = line.split("\\|", -1);

                if (p.length == 4 && p[0].equals(username)) {
                    return p;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void add(String user, String pass, String question, String answer) throws IOException {
        String line = user + "|" + pass + "|" + question + "|" + answer + System.lineSeparator();
        Files.writeString(FILE, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void updatePassword(String username, String newPass) throws IOException {
        List<String> out = new ArrayList<>();
        for (String line : Files.readAllLines(FILE)) {
            String[] p = line.split("\\|", -1);
            if (p.length == 4 && p[0].equals(username)) {
                line = p[0] + "|" + newPass + "|" + p[2] + "|" + p[3];
            }
            out.add(line);
        }
        Files.write(FILE, out);
    }

    public static int count() {
        if (!Files.exists(FILE)) {
            return 0;
        }
        try {
            return (int) Files.readAllLines(FILE).stream().filter(l -> !l.isBlank()).count();
        } catch (IOException e) {
            return 0;
        }
    }

}
