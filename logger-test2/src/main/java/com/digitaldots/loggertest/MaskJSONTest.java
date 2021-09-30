package com.digitaldots.loggertest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MaskJSONTest {
    static Set fieldSet = new HashSet();
    static List fieldNames = Arrays.asList("cardNumber", "cvv", "expDate");

    public static void main(String[] args) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream stream = Files.lines(Paths.get("D:\\Spring\\logger-test2\\src\\main\\resources\\abc.json"), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create GSON object
        // apply NullSearialization and Pretty formatting by GSON Builder
        Gson gson = getJsonBuilder().create();
        AccountDetail accounDetail = gson.fromJson(contentBuilder.toString(), AccountDetail.class);
        mask(accounDetail);
        System.out.println(gson.toJson(accounDetail));
    }

    public static GsonBuilder getJsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // Setting for formatted output and serialize null value
        builder.setPrettyPrinting().serializeNulls();

        return builder;
    }

    public static void mask(Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            Object value = null;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                value = fields[i].get(object);
                if (value != null && fieldSet.add(fields[i].getName())) {
                    if (fields[i].getType().isArray() || fields[i].getType().getCanonicalName().startsWith("com.digitaldots.loggertest")) {
                        mask(value);
                    } else {
                        if (fieldNames.contains(fields[i].getName()) && fields[i].get(object) != null) {
                            fields[i].set(object, replaceDigits((String) fields[i].get(object)));
                        }
                    }
                }

            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private static String replaceDigits(String text) {
        StringBuffer buffer = new StringBuffer(text.length());
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "X");
        }
        return buffer.toString();
    }

}