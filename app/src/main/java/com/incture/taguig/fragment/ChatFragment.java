package com.incture.taguig.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.incture.taguig.R;
import com.incture.taguig.adapter.MessageListAdapter;
import com.incture.taguig.models.BaseMessage;
import com.incture.taguig.network.BotApplication;
import com.incture.taguig.network.ChatbotListener;
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
import java.util.Objects;

import static com.incture.taguig.utils.Constants.REQ_CODE_SPEECH_INPUT;


public class ChatFragment extends Fragment implements VolleyListener, ChatbotListener {
    private static final int RESULT_OK =-1 ;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> messageList = new ArrayList<>();
    private EditText mEditText;
    private ImageView ivSend;
    private ImageView ivMic;
    Connector connector;
    private static final int POST = Request.Method.POST;

    String converstaion_ID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_chat, container, false);
        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list);
        mEditText = view.findViewById(R.id.edittext_chatbox);
        ivSend = view.findViewById(R.id.ivSend);
        ivMic=view.findViewById(R.id.ivMic);
        converstaion_ID= String.valueOf(System.currentTimeMillis());
        initWidgets();
        initAdapter();
        return view;
    }

    private void initAdapter() {

       // messageList.add(new BaseMessage("Hi , What  can i help you with ? .", "Recieve", new ArrayList<>()));
            messageList.add(new BaseMessage("Hi,What can i help you with ?","Receive",new ArrayList<HashMap<String, String>>()));
        mMessageAdapter = new MessageListAdapter(getActivity(), messageList,this);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setItemAnimator(new DefaultItemAnimator());

        mMessageRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mMessageRecycler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMessageRecycler.smoothScrollToPosition(
                                    mMessageRecycler.getAdapter().getItemCount() - 1);
                        }
                    }, 100);
                }
            }
        });
    }
 public void initWidgets(){

     mMessageAdapter = new MessageListAdapter(getActivity(), messageList,this);
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
                 fetchResponse(content,content);
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
                    fetchResponse(result.get(0),result.get(0));
                }
                break;
            }

        }
    }

    private void fetchResponse(String content,String value) {
        messageList.add(new BaseMessage(content, "Send"));
        mMessageAdapter.notifyDataSetChanged();

        int itemCount = Objects.requireNonNull(mMessageRecycler.getAdapter()).getItemCount();
        mMessageRecycler.scrollToPosition(itemCount - 1);

        try {
            call(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mEditText.getText().clear();
        //uiComponents.forceHideKeyboard();
    }

   /* private void call(String content) throws JSONException {
        connect("https://api.cai.tools.sap/build/v1/dialog", POST, createBody(content), new HashMap<String, String>(0));
        //https://api.cai.tools.sap/build/v1/dialog
        //https://api.recast.ai/build/v1/dialog
    }*/



    private void call(String content) throws JSONException {

        connect("https://api.cai.tools.sap/build/v1/dialog", POST, createBody(content), ChatFragment.this,new HashMap<String, String>());

    }
//
    private JSONObject createBody(String content) throws JSONException {
        JSONObject body = getBody();
        body.put("log_level","info");
        body.put("message", getMessage(content));
        body.put("memory",getMemory());
        body.put("merge_memory",true);
        body.put("language","en");

        return body;
    }

    private JSONObject getBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("conversation_id",converstaion_ID);
        return jsonObject;
    }

    private JSONObject getMessage(String content) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
        jsonObject.put("type", "text");
        return jsonObject;
    }

    private JSONObject getMemory() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        JSONObject user=new JSONObject();
        user.put("isAdmin", true);
        user.put("userId", "P000057");
        user.put("userName","Shruti Patra");

        jsonObject.put("user",user);

        return jsonObject;
    }


    @Override
    public void onResponseReceived(String URL, Object obj) {

        Log.d("ResponseChatbot",obj.toString());

        JSONObject mainObject;
        try {
            mainObject = new JSONObject(obj.toString());
            JSONObject results = mainObject.optJSONObject("results");
            JSONArray array = results.optJSONArray("messages");
            StringBuilder response = new StringBuilder();

            ArrayList<HashMap<String,String>> actions=new ArrayList<>();

            if (array.length() > 0) {

                for (int d = 0; d < array.length(); d++) {


                    actions.clear();

                    JSONObject msg = array.optJSONObject(d);

                    if (msg.getString("type").equalsIgnoreCase("text"))
                    {

                        // response.append(msg.optString("content")) ;


                        messageList.add(new BaseMessage(msg.optString("content"), "Recieve",new ArrayList<HashMap<String, String>>()));


                    }else if (msg.getString("type").equalsIgnoreCase("quickReplies"))
                    {

                        JSONObject inner=msg.getJSONObject("content");

                        if (array.length()>1)
                        {
                            response.append("\n\n");
                        }


                        response.append(inner.getString("title"));


                        JSONArray inner_array=inner.getJSONArray("buttons");

                        for (int i=0;i<inner_array.length();i++)
                        {
                            JSONObject temp=inner_array.getJSONObject(i);

                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("title",temp.getString("title"));
                            hashMap.put("value",temp.getString("value"));

                            actions.add(hashMap);

                            //actions.add(temp.getString("title"));

                        }

                        messageList.add(new BaseMessage(inner.getString("title"), "Recieve",actions));

                    }

                }
            } else {
                response.append("I didn't get that, try something else");
            }

            //messageList.add(new BaseMessage(response.toString(), "Recieve",actions));
            mMessageAdapter.notifyDataSetChanged();

            mMessageRecycler.scrollToPosition(mMessageRecycler.getAdapter().getItemCount() - 1);

            performAction(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void connect(final String URL, int POST, final JSONObject body, final VolleyListener volleyListener, final HashMap<String, String> requestHeaders) {


        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                POST,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("RespondedData", body.toString());
                        Log.d("ResponseServer", response.toString());
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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");

                //workbox
               // String token ="Token 02cdb6e3d294465c6ae8c1b9417b45be";

                // My Token
                String token="Token 69b7bb0fef322bfae02f326d6a08e4ec";
                headers.put("Authorization", token);
                headers.put("x-uuid", "84004a10-3828-4845-87e6-6a7d063d94af");
                return headers;
            }
        };


        BotApplication.getInstance(getActivity()).addToRequestQueue(jsonRequest);

    }

    private void performAction(String response) {
        if (response.contains("Completed")){

        }
    }

    @Override
    public void showFab() {

    }

    @Override
    public void performAction(String response, String comment) {

        fetchResponse(response,comment);
    }

    @Override
    public int getBotContext() {
        return 0;
    }

}
