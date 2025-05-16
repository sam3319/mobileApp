package com.example.chapter10openchallenge;

import java.util.HashMap;
import java.util.Map;

public class TextManager {
    private static final Map<String, Map<String, String>> texts = new HashMap<>();

    static {
        // 한국어 텍스트
        Map<String, String> koTexts = new HashMap<>();
        koTexts.put("food_title", "김밥");
        koTexts.put("recipe_title", "레시피");
        koTexts.put("recipe_step1", "1. 밥에 양념을 넣고 섞기");
        koTexts.put("recipe_step2", "2. 김 위에 밥을 펴 깔기");
        koTexts.put("recipe_step3", "3. 준비된 재료를 올리기");
        koTexts.put("recipe_step4", "4. 김밥 말기");
        koTexts.put("recipe_step5", "5. 적당한 크기로 썰기");

        // 영어 텍스트
        Map<String, String> enTexts = new HashMap<>();
        enTexts.put("food_title", "Hamburger");
        enTexts.put("recipe_title", "Recipe");
        enTexts.put("recipe_step1", "1. Prepare beef patty");
        enTexts.put("recipe_step2", "2. Cook patty on grill");
        enTexts.put("recipe_step3", "3. Toast the buns");
        enTexts.put("recipe_step4", "4. Add lettuce, tomato, and sauce");
        enTexts.put("recipe_step5", "5. Assemble the hamburger");

        texts.put("ko", koTexts);
        texts.put("en", enTexts);
    }

    public static String getText(String key, String language) {
        if (texts.containsKey(language) && texts.get(language).containsKey(key)) {
            return texts.get(language).get(key);
        }
        return "Text not found";
    }
}
