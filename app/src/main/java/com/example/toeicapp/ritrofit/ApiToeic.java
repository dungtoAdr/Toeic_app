package com.example.toeicapp.ritrofit;

import com.example.toeicapp.model.QuestionModel;
import com.example.toeicapp.model.Topics;
import com.example.toeicapp.model.Vocabularies;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiToeic {
    @GET("test_part1.php")
    Observable<QuestionModel> getQuestion();

    @GET("get_topics.php")
    Observable<Topics> getTopics();

    @GET("get_vocabulary_by_topic.php")
    Observable<Vocabularies> getVoca(
            @Query("topic_id") int topic_id
    );
}
