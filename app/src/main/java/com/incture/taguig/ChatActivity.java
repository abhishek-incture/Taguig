package com.incture.taguig;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;

import com.incture.taguig.adapter.MessageListAdapter;
import com.incture.taguig.models.BaseMessage;
import com.incture.taguig.network.Connector;
import com.incture.taguig.network.VolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import static com.incture.taguig.utils.Constants.REQ_CODE_SPEECH_INPUT;


public class ChatActivity extends AppCompatActivity implements VolleyListener {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> messageList = new ArrayList<>();
    private EditText mEditText;
    private ImageView ivSend;
    Connector connector = new Connector();
    private static final int POST = Request.Method.POST;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mEditText = findViewById(R.id.edittext_chatbox);
        ivSend = findViewById(R.id.ivSend);

        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setItemAnimator(new DefaultItemAnimator());

        initTextbox();

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEditText.getText().toString();
                if (content.isEmpty()) {
                    startVoiceInput();
                } else {
                    fetchResponse(content);
                }
            }
        });
    }

    private void initTextbox() {
        TextWatcher mTextEditorWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                //This sets a textview to the current length
                if (s.length() == 0) {
                    // Show mic
                    ivSend.setImageResource(R.drawable.ic_mic);
                } else {
                    // Show send
                    ivSend.setImageResource(R.drawable.ic_send);
                }
            }
        };
        mEditText.addTextChangedListener(mTextEditorWatcher);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Oops... try again", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    fetchResponse(result.get(0));
                }
                break;
            }

        }
    }

    private void fetchResponse(String content) {
        messageList.add(new BaseMessage(content, "Send"));
        mMessageAdapter.notifyDataSetChanged();

        mMessageRecycler.scrollToPosition(mMessageRecycler.getAdapter().getItemCount() - 1);

        try {
            call(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mEditText.getText().clear();
    }

    private void call(String content) throws JSONException {
        connector.connect("https://api.recast.ai/build/v1/dialog", POST, createBody(content), ChatActivity.this, new HashMap<String, String>(0));

    }

    private JSONObject createBody(String content) throws JSONException {
        JSONObject body = getBody();
        body.put("message", getMessage(content));
        return body;
    }

    private JSONObject getBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("conversation_id", 1);
        return jsonObject;
    }

    private JSONObject getMessage(String content) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
        jsonObject.put("type", "text");
        return jsonObject;
    }


    @Override
    public void onResponseReceived(String URL, Object obj) {
        JSONObject mainObject;
        try {
            mainObject = new JSONObject(obj.toString());
            JSONObject results = mainObject.optJSONObject("results");
            JSONArray array = results.optJSONArray("messages");
            String response = "";
            if (array.length() > 0) {
                for (int d = 0; d < array.length(); d++) {
                    JSONObject msg = array.optJSONObject(d);
                    response = msg.optString("content");
                }
            } else {
                response = "I didn't get that, try something else";
            }
            messageList.add(new BaseMessage(response, "Recieve"));
            mMessageAdapter.notifyDataSetChanged();

            mMessageRecycler.scrollToPosition(mMessageRecycler.getAdapter().getItemCount() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
