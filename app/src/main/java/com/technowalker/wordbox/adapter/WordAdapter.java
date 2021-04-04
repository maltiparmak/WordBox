package com.technowalker.wordbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.technowalker.wordbox.R;
import com.technowalker.wordbox.objects.Word;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    ArrayList<Word> wordList;
    Context context;
    public WordAdapter(@NonNull Context context, ArrayList<Word> wordList) {
        super(context, R.layout.adapter_view_layout, wordList);
        this.context = context;
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View customView = layoutInflater.inflate(R.layout.adapter_view_layout, parent, false);

        TextView wordText = customView.findViewById(R.id.wordText);
        wordText.setText(wordList.get(position).getWordText());

        TextView meanText = customView.findViewById(R.id.meanText);
        meanText.setText(wordList.get(position).getMeanText());

        TextView propText = customView.findViewById(R.id.propText);
        propText.setText(wordList.get(position).getPropText());

        TextView langText = customView.findViewById(R.id.langText);
        langText.setText(wordList.get(position).getLangText());


        return customView;
    }
}
