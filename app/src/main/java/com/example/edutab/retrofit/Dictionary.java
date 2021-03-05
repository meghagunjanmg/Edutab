package com.example.edutab.retrofit;

import com.example.DictionaryMainResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Dictionary {

    @Headers("application/json: application/json")
    @GET("entries/en/{word}")
    Call<List<DictionaryMainResponse>> getWordMeaning(@Path(value="word") String word);

}
