
package com.example;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phonetic implements Serializable
{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("audio")
    @Expose
    private String audio;
    private final static long serialVersionUID = -2676149252442574489L;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

}
