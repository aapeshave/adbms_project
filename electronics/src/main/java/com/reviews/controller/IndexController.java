package com.reviews.controller;

import com.google.gson.Gson;
import com.reviews.pojo.Metadata;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by ajinkya on 4/25/17.
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {

        try (Stream<Path> paths = Files.walk(Paths.get("/Users/ajinkya/Documents/adbms_project/electronics/input/hadoop/pig/"))) {
            Collection<String> asinCollection = new HashSet<>();
            Jedis jedis = new Jedis("localhost");

            addTopTenCategoriesToRedis(paths, asinCollection, jedis);

            //Now Store asins in data base
            storeAsinsToRedis(asinCollection, jedis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Hello from Spring Boot";
    }

    private void addTopTenCategoriesToRedis(Stream<Path> paths, Collection<String> asinCollection, Jedis jedis) {
        paths.forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                System.out.println("Reading file: " + filePath);
                @NotNull
                File currentFile = filePath.toFile();
                String currentLine;
                if (filePath.toString().contains("part")) {
                    List<String> toStoreInRedis = new ArrayList<>();

                    String category = "";
                    try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                        while ((currentLine = reader.readLine()) != null) {
                            String[] row = currentLine.split("\t");
                            toStoreInRedis.add(row[0]);
                            category = row[2];
                            asinCollection.add(row[0]);
                        }
                        String key = "top_10_" + category;
                        jedis.set(key, toStoreInRedis.toString());
                        System.out.println("Key in redis: " + key);
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void storeAsinsToRedis(Collection<String> asinCollection, Jedis jedis) {
        Gson gson = new Gson();
        File metadataFile = new File("/Users/ajinkya/Downloads/metadata.json");
        String currentLine;
        try (BufferedReader metadataFileReader = new BufferedReader(new FileReader(metadataFile))) {
            while ((currentLine = metadataFileReader.readLine()) != null) {
                Metadata sampleMetadata = gson.fromJson(currentLine, Metadata.class);
                if (StringUtils.isNotBlank(sampleMetadata.getAsin())) {
                    if (asinCollection.contains(sampleMetadata.getAsin())) {
                        jedis.set(sampleMetadata.getAsin(), gson.toJson(sampleMetadata));
                        System.out.println("Added asin to db: " + sampleMetadata.getAsin());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jedis.close();
    }

    @RequestMapping(value = "/search_category", method = RequestMethod.GET)
    public List<Metadata> findTopTenProducts(@RequestParam(name = "searchParam", required = false) String searchParam) {
        Gson gson = new Gson();
        if (StringUtils.isNotBlank(searchParam)) {
            List<Metadata> results = new ArrayList<>();
            Jedis jedis = new Jedis("localhost");
            String key = "top_10_" + searchParam;
            if (StringUtils.isNotBlank(jedis.get(key))) {
                String result = jedis.get(key);
                result = result.replaceAll("\\[", "").replaceAll("\\]", "");
                String[] strArray = result.split(",");
                for (String s : strArray) {
                    String metadataInString = jedis.get(s.trim());
                    Metadata fromDB = gson.fromJson(metadataInString, Metadata.class);
                    results.add(fromDB);
                }
                return results;
            }
        }
        return null;
    }
}
