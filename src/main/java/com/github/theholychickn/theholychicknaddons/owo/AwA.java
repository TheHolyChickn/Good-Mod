package com.github.theholychickn.theholychicknaddons.owo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AwA {
    private static final File CONFIG_FILE = new File("config/drops.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Map<String, Integer> owowo = new LinkedHashMap<>();

    public void loadConfig() {
        try{
            if (CONFIG_FILE.exists()) {
                FileReader reader = new FileReader(CONFIG_FILE);
                AwA config = GSON.fromJson(reader, AwA.class);
                this.owowo = config.owowo;
                reader.close();
            } else {
                saveConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            FileWriter writer = new FileWriter(CONFIG_FILE);
            GSON.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItem(String item) {
        owowo.put(item, owowo.getOrDefault(item, 0) + 1);
    }

    public int getItemCount(String item) {
        return owowo.getOrDefault(item, 0);
    }

}
