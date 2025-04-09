package com.example.toeicapp.utils;

import com.example.toeicapp.model.PracticeSession;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class Utils {
//    public static final String BASE_URL = "http://192.168.100.113/toeic/";
    public static final String BASE_URL = "http://183.182.118.181/toeic/";
    public static List<Question> questions_answer = new ArrayList<>();
    public static List<Questions> questions_model_answer = new ArrayList<>();
    public static String current_user_id;
    public static List<PracticeSession> practiceSessions = new ArrayList<>();
    public static List<PracticeSession> practiceSessions_all = new ArrayList<>();
}