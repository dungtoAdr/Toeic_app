package com.example.toeicapp.activty;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toeicapp.R;
import com.example.toeicapp.ZoomOutPageTransformer;
import com.example.toeicapp.fragment.QuestionFragment;
import com.example.toeicapp.model.Question;
import com.example.toeicapp.ritrofit.ApiToeic;
import com.example.toeicapp.ritrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SlideActivity extends FragmentActivity {
    private ViewPager2 viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        // Gọi API và khởi tạo ViewPager Adapter
        ApiToeic apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        compositeDisposable.add(apiToeic.getQuestion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questionModel -> {
                            if (questionModel.isSuccess()) {
                                List<Question> questions = questionModel.getQuestions();
                                pagerAdapter = new ScreenSlidePagerAdapter(this, questions);
                                viewPager.setAdapter(pagerAdapter);
                            }
                        },
                        throwable -> Log.d("API_ERROR", throwable.getMessage())
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private final List<Question> questions;

        public ScreenSlidePagerAdapter(FragmentActivity fa, List<Question> questions) {
            super(fa);
            this.questions = questions;
        }

        @Override
        public QuestionFragment createFragment(int position) {
            return QuestionFragment.newInstance(questions.get(position));
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }
    }
}
