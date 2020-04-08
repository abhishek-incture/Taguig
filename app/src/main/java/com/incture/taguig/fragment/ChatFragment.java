package com.incture.taguig.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.incture.taguig.ChatActivity;
import com.incture.taguig.R;
import com.incture.taguig.adapter.MessageListAdapter;
import com.incture.taguig.models.BaseMessage;
import com.incture.taguig.network.BotApplication;
import com.incture.taguig.network.Connector;
import com.incture.taguig.network.VolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.incture.taguig.utils.Constants.REQ_CODE_SPEECH_INPUT;


public class ChatFragment extends Fragment implements VolleyListener {
    private static final int RESULT_OK =-1 ;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> messageList = new ArrayList<>();
    private EditText mEditText;
    private ImageView ivSend;
    private ImageView ivMic;
    Connector connector = new Connector();
    private static final int POST = Request.Method.POST;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_chat, container, false);
        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list);
        mEditText = view.findViewById(R.id.edittext_chatbox);
        ivSend = view.findViewById(R.id.ivSend);
        ivMic=view.findViewById(R.id.ivMic);
        initWidgets();
        return view;
    }
 public void initWidgets(){

     mMessageAdapter = new MessageListAdapter(getActivity(), messageList);
     mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
     mMessageRecycler.setAdapter(mMessageAdapter);
     mMessageRecycler.setItemAnimator(new DefaultItemAnimator());

   //  initTextbox();

     ivMic.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startVoiceInput();
         }
     });

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
            Toast.makeText(getActivity(), "Oops... try again", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        connect("https://api.cai.tools.sap/build/v1/dialog", POST, createBody(content), new HashMap<String, String>(0));
        //https://api.cai.tools.sap/build/v1/dialog
        //https://api.recast.ai/build/v1/dialog
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

    public void connect(final String URL, int POST, JSONObject body, final HashMap<String, String> requestHeaders) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                POST,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d("resp", response.toString());
                        onResponseReceived(URL, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("resp", "Error: " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token 399cfed8c6ad414d38c228899190bcd1");
                return headers;
                //031339e46e8471dc638739cfa9dd088f
            }
        };
        BotApplication.getInstance().addToRequestQueue(jsonRequest);

    }

}
