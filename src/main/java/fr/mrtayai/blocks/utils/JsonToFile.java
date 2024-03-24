package fr.mrtayai.blocks.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JsonToFile {

    private JsonObject jsonObject;
    private String filePath;

    public JsonToFile(JsonObject jsonObject, String filePath){
        this.jsonObject = jsonObject;
        this.filePath = filePath;
        this.writeJsonToFile();
    }

    private void writeJsonToFile(){
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(this.filePath)){
            gson.toJson(this.jsonObject, writer);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
