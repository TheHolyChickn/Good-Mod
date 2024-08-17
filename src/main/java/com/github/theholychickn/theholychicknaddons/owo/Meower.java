package com.github.theholychickn.theholychicknaddons.owo;
import com.github.theholychickn.theholychicknaddons.owo.Yapping;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Meower {
    private static final Gson MEOW = new GsonBuilder().setPrettyPrinting().create();
    private static final File MEOW_FILE = new File("config/goodmod.json");

    public static Meow meow;

    public static void loadMeow() {
        if (MEOW_FILE.exists()) {
            try (FileReader reader = new FileReader(MEOW_FILE)) {
                meow = MEOW.fromJson(reader, Meow.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            meow = new Meow();
            saveMeow();
        }
        Yapping.log("Meower loaded");
    }

    public static void saveMeow() {
        try (FileWriter writer = new FileWriter(MEOW_FILE)) {
            MEOW.toJson(meow, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
