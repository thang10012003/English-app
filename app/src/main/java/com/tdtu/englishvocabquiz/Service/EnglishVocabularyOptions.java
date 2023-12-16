package com.tdtu.englishvocabquiz.Service;

import java.util.HashMap;
import java.util.Map;

public class EnglishVocabularyOptions {
    public EnglishVocabularyOptions() {

    }
    public String[] topics (){
        String[] topics = {
                "All",
                "Animals",
                "Colors",
                "Fruits",
                "Family",
                "Weather",
                "Transportation",
                "Clothing",
                "Numbers",
                "Food",
                "School"
        };
        return topics;
    }
    public Map optionsSelected(String selectedTopic){

        if (selectedTopic.equals(topics()[1])) {
            return getAnimals();
        } else if (selectedTopic.equals(topics()[2])) {
            return getColors();
        } else if (selectedTopic.equals(topics()[3])) {
            return getFruits();
        } else if (selectedTopic.equals(topics()[4])) {
            return getFamily();
        } else if (selectedTopic.equals(topics()[5])) {
            return getWeather();
        } else if (selectedTopic.equals(topics()[6])) {
            return getTransportation();
        } else if (selectedTopic.equals(topics()[7])) {
            return getClothing();
        } else if (selectedTopic.equals(topics()[8])) {
            return getNumbers();
        } else if (selectedTopic.equals(topics()[9])) {
            return getFood();
        } else if (selectedTopic.equals(topics()[10])) {
            return getSchool();
        }
        return getAllTopics();
    }
    public Map getAllTopics(){
        Map<String, String> combinedMap = new HashMap<>();

        // Combine vocabulary from different topics
        combinedMap.putAll(getNumbers());
        combinedMap.putAll(getAnimals());
        combinedMap.putAll(getColors());
        combinedMap.putAll(getClothing());
        combinedMap.putAll(getTransportation());
        combinedMap.putAll(getFood());
        combinedMap.putAll(getSchool());
        combinedMap.putAll(getFamily());
        combinedMap.putAll(getWeather());

        return combinedMap;
    }
    public static Map<String, String> getSchool() {
        String[] schoolEnglish = {
                "Book", "Pen", "Notebook", "Teacher", "Student",
                "Classroom", "Homework", "Exam", "Backpack", "Chalkboard"
        };

        String[] schoolVietnamese = {
                "Sách", "Bút", "Sổ tay", "Giáo viên", "Học sinh",
                "Phòng học", "Bài tập về nhà", "Kỳ thi", "Balo", "Bảng đen"
        };

        Map<String, String> schoolVocabulary = new HashMap<>();

        for (int i = 0; i < schoolEnglish.length; i++) {
            schoolVocabulary.put(schoolEnglish[i], schoolVietnamese[i]);
        }

        return schoolVocabulary;
    }
    public static Map<String, String> getFood() {
        String[] foodEnglish = {
                "Pizza", "Burger", "Pasta", "Sushi", "Salad",
                "Ice Cream", "Sandwich", "Soup", "Cake", "Fruit"
        };

        String[] foodVietnamese = {
                "Pizza", "Bánh burger", "Mì ống", "Sushi", "Rau trộn",
                "Kem", "Bánh mì kẹp", "Súp", "Bánh ngọt", "Trái cây"
        };

        Map<String, String> foodVocabulary = new HashMap<>();

        for (int i = 0; i < foodEnglish.length; i++) {
            foodVocabulary.put(foodEnglish[i], foodVietnamese[i]);
        }

        return foodVocabulary;
    }
    public static Map<String, String> getNumbers() {
        String[] numbersEnglish = {
                "One", "Two", "Three", "Four", "Five",
                "Six", "Seven", "Eight", "Nine", "Ten"
        };

        String[] numbersVietnamese = {
                "Một", "Hai", "Ba", "Bốn", "Năm",
                "Sáu", "Bảy", "Tám", "Chín", "Mười"
        };

        Map<String, String> numbersVocabulary = new HashMap<>();

        for (int i = 0; i < numbersEnglish.length; i++) {
            numbersVocabulary.put(numbersEnglish[i], numbersVietnamese[i]);
        }

        return numbersVocabulary;
    }
    public static Map<String, String> getClothing() {
        String[] clothingEnglish = {
                "Shirt", "Pants", "Dress", "Skirt", "Jacket",
                "Sweater", "T-shirt", "Hat", "Shoes", "Socks"
        };

        String[] clothingVietnamese = {
                "Áo sơ mi", "Quần", "Váy", "Chân váy", "Áo khoác",
                "Áo len", "Áo thun", "Mũ", "Giày", "Tất"
        };

        Map<String, String> clothingVocabulary = new HashMap<>();

        for (int i = 0; i < clothingEnglish.length; i++) {
            clothingVocabulary.put(clothingEnglish[i], clothingVietnamese[i]);
        }

        return clothingVocabulary;
    }
    public static Map<String, String> getTransportation() {
        String[] transportationEnglish = {
                "Car", "Bus", "Train", "Bicycle", "Motorcycle",
                "Subway", "Tram", "Boat", "Airplane", "Helicopter"
        };

        String[] transportationVietnamese = {
                "Ô tô", "Xe buýt", "Tàu hỏa", "Xe đạp", "Xe máy",
                "Tàu điện ngầm", "Xe điện", "Thuyền", "Máy bay", "Trực thăng"
        };

        Map<String, String> transportationVocabulary = new HashMap<>();

        for (int i = 0; i < transportationEnglish.length; i++) {
            transportationVocabulary.put(transportationEnglish[i], transportationVietnamese[i]);
        }

        return transportationVocabulary;
    }
    public static Map<String, String> getFamily() {
        String[] familyEnglish = {
                "Mother", "Father", "Brother", "Sister", "Grandmother",
                "Grandfather", "Aunt", "Uncle", "Cousin", "Nephew",
                "Niece", "Son", "Daughter", "Husband", "Wife"
        };

        String[] familyVietnamese = {
                "Mẹ", "Bố", "Anh trai", "Em gái", "Bà",
                "Ông", "Dì", "Chú", "Cô", "Cháu trai",
                "Cháu gái", "Con trai", "Con gái", "Chồng", "Vợ"
        };

        Map<String, String> familyVocabulary = new HashMap<>();

        for (int i = 0; i < familyEnglish.length; i++) {
            familyVocabulary.put(familyEnglish[i], familyVietnamese[i]);
        }

        return familyVocabulary;
    }
    public static Map<String, String> getWeather() {
        String[] weatherEnglish = {
                "Sun", "Rain", "Cloud", "Wind", "Snow",
                "Thunderstorm", "Fog", "Temperature", "Humidity", "Forecast"
        };

        String[] weatherVietnamese = {
                "Nắng", "Mưa", "Đám mây", "Gió", "Tuyết",
                "Bão", "Sương mù", "Nhiệt độ", "Độ ẩm", "Dự báo thời tiết"
        };

        Map<String, String> weatherVocabulary = new HashMap<>();

        for (int i = 0; i < weatherEnglish.length; i++) {
            weatherVocabulary.put(weatherEnglish[i], weatherVietnamese[i]);
        }

        return weatherVocabulary;
    }
    public Map getAnimals(){
        String[] animalsEnglish = {
                "Cat", "Dog", "Elephant", "Giraffe", "Tiger",
                "Lion", "Monkey", "Horse", "Bird", "Fish",
                "Dolphin", "Kangaroo", "Penguin", "Snake", "Frog",
                "Butterfly", "Panda", "Bear", "Zebra", "Koala"
        };

        // Array of Vietnamese definitions corresponding to the English words
        String[] animalsVietnamese = {
                "Mèo", "Chó", "Voi", "Hươu cao cổ", "Hổ",
                "Sư tử", "Khỉ", "Ngựa", "Chim", "Cá",
                "Cá heo", "Kangaroo", "Chim cánh cụt", "Rắn", "Ếch",
                "Bướm", "Gấu trúc", "Gấu", "Ngựa vằn", "Gấu koala"
        };
        Map<String, String> animalsVocabulary = new HashMap<>();

        // Populate the HashMap
        for (int i = 0; i < animalsEnglish.length; i++) {
            animalsVocabulary.put(animalsEnglish[i], animalsVietnamese[i]);
        }

        return animalsVocabulary;
    }
    public Map getColors(){
        String[] colorsEnglish = {
                "Red", "Blue", "Green", "Yellow", "Purple",
                "Orange", "Pink", "Brown", "Black", "White",
                "Gray", "Silver", "Gold", "Cyan", "Magenta",
                "Turquoise", "Lime", "Indigo", "Maroon", "Navy"
        };

        // Array of Vietnamese definitions corresponding to the English words
        String[] colorsVietnamese = {
                "Đỏ", "Xanh dương", "Xanh lá cây", "Vàng", "Tím",
                "Cam", "Hồng", "Nâu", "Đen", "Trắng",
                "Xám", "Bạc", "Vàng", "Cyan", "Magenta",
                "Turquoise", "Lime", "Indigo", "Đỏ thẫm", "Hải quân"
        };

        // Create a HashMap to store English words and their Vietnamese definitions
        Map<String, String> colorsVocabulary = new HashMap<>();

        // Populate the HashMap
        for (int i = 0; i < colorsEnglish.length; i++) {
            colorsVocabulary.put(colorsEnglish[i], colorsVietnamese[i]);
        }

        return colorsVocabulary;
    }
    public Map<String, String> getFruits() {
        String[] fruitsEnglish = {
                "Apple", "Banana", "Orange", "Grapes", "Strawberry",
                "Watermelon", "Mango", "Pineapple", "Kiwi", "Peach",
                "Pear", "Cherry", "Plum", "Blueberry", "Raspberry",
                "Avocado", "Coconut", "Lemon", "Lime", "Pomegranate"
        };

        String[] fruitsVietnamese = {
                "Quả táo", "Quả chuối", "Cam", "Nho", "Dâu",
                "Dưa hấu", "Xoài", "Dứa", "Kiwi", "Đào",
                "Lê", "Anh đào", "Mận", "Việt quất", "Mâm xôi",
                "Bơ", "Dừa", "Chanh", "Chanh xanh", "Lựu"
        };

        Map<String, String> fruitsVocabulary = new HashMap<>();

        for (int i = 0; i < fruitsEnglish.length; i++) {
            fruitsVocabulary.put(fruitsEnglish[i], fruitsVietnamese[i]);
        }

        return fruitsVocabulary;
    }

}
