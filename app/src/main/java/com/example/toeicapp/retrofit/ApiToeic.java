package com.example.toeicapp.retrofit;


import com.example.toeicapp.model.ExamModel;
import com.example.toeicapp.model.QuestionModel;
import com.example.toeicapp.model.QuestionsModel;
import com.example.toeicapp.model.Topics;
import com.example.toeicapp.model.User;
import com.example.toeicapp.model.UserModel;
import com.example.toeicapp.model.Vocabularies;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiToeic {
    @GET("part1.php")
    Observable<QuestionModel> getPart1();

    @GET("part2.php")
    Observable<QuestionModel> getPart2();

    @GET("part3.php")
    Observable<QuestionsModel> getPart3();

    @GET("part4.php")
    Observable<QuestionsModel> getPart4();

    @GET("part5.php")
    Observable<QuestionModel> getPart5();

    @GET("part6.php")
    Observable<QuestionsModel> getPart6();

    @GET("part7.php")
    Observable<QuestionsModel> getPart7();

    @GET("get_topics.php")
    Observable<Topics> getTopics();

    @GET("get_vocabulary_by_topic.php")
    Observable<Vocabularies> getVoca(
            @Query("topic_id") int topic_id
    );

    @GET("get_exams.php")
    Observable<ExamModel> getExams();

    @POST("signup.php")
    Observable<UserModel> signUp(
            @Body User user
    );

}
