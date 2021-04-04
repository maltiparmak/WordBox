package com.technowalker.wordbox.objects;

import java.io.Serializable;

public class Word implements Serializable {

    private String wordText;
    private String meanText;
    private String propText;
    private String langText;

    public Word (String wordText, String meanText, String langText,String propText ){
        this.wordText = wordText;
        this.meanText = meanText;
        this.langText = langText;
        this.propText = propText;

    }

    public String getWordText() {
        return wordText;
    }
    public String getMeanText() {
        return meanText;
    }
    public String getLangText() {
        return langText;
    }
    public String getPropText() {
        return propText;
    }
}
