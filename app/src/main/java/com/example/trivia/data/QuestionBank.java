package com.example.trivia.data;

// https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements.json

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.trivia.controller.AppController.TAG;

public class QuestionBank {

    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements.json";

    ArrayList<Question> questionArrayList = new ArrayList<>();

    public List<Question> getQuestions(final AnswerListAsyncResponse callback){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.d("JSON Stuff", "onResponse: " + response);

                for(int i=0;i<response.length();i++){
                    try {

                        Question q = new Question();
                        q.setQuest(response.getJSONArray(i).get(0).toString());
                        q.setAnswer(response.getJSONArray(i).getBoolean(1));

                        questionArrayList.add(q);
//                        Log.d("jason", "onResponse: "+ response.getJSONArray(i).get(0).toString());
//                        Log.d("jason2", "onResponse: "+ response.getJSONArray(i).getBoolean(1));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                
                if(callback != null){
                    callback.processFinished(questionArrayList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error message : ", "onResponse: " + error);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;

    }

}
