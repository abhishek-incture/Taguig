package com.incture.taguig;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.adapter.Question2Adapter;
import com.incture.taguig.adapter.QuestionAdapter;
import com.incture.taguig.models.Question2Model;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private List<String> questionModelList;
    private RecyclerView recyclerView2;
    private Question2Adapter adapter2;
    private List<Question2Model> question2ModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        recyclerView = findViewById(R.id.recyclerView);
        questionModelList= new ArrayList<>();
        init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new QuestionAdapter(this,questionModelList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView2 = findViewById(R.id.recyclerView2);
        question2ModelList= new ArrayList<>();
        init2();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        adapter2 = new Question2Adapter(this,question2ModelList);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(adapter2);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider2));
        recyclerView2.addItemDecoration(itemDecorator);


    }

    private void init2() {
        String[] options = {"Choose--","option1","option2","option3"};

        Question2Model question2Model;
        question2Model = new Question2Model("What are the Primary reason you think our state govt. is " +
                "social media technologoies?",options);
        question2ModelList.add(question2Model);
        String[] options1 = {"Choose--","Select1","Select2","Select3"};

        question2Model = new Question2Model("What do you think, Our city Govt. Media adoption is" +
                "primarily through?",options1);
        question2ModelList.add(question2Model);

        question2Model = new Question2Model("How often you use social media?",options);
        question2ModelList.add(question2Model);

        question2Model = new Question2Model("Which Social Media, you get mostly of the daily news" +
                "from?",options1);
        question2ModelList.add(question2Model);
    }

    private void init() {
        questionModelList.add("Our Govt has clear direction in using social media for " +
                "Public Policy.");
        questionModelList.add("Our Govt is standarded in keeping the official matters" +
                "from Social Media.");
        questionModelList.add("Our Govt has best practices in managing from social" +
                "media.");
    }


    public void onback(View view) {
        onBackPressed();
    }

    public void Submit(View view) {
        onBackPressed();
    }
}
