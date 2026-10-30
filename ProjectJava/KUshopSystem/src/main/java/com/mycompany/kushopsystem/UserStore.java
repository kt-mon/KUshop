package com.mycompany.kushopsystem;

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
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void add(String user, String pass, String question, String answer) throws IOException {
        String line = user + "|" + pass + "|" + question + "|" + answer + System.lineSeparator();
        Files.writeString(FILE, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
     
}
