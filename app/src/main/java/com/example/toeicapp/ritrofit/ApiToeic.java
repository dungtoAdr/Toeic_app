package com.example.toeicapp.ritrofit;

import com.example.toeicapp.model.QuestionModel;
import com.example.toeicapp.model.Topics;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiToeic {
    @GET("test_part1.php")
    Observable<QuestionModel> getQuestion();

    @GET("get_topics.php")
    Observable<Topics> getTopics();
}
