package com.techwirz.surveyapp.Interfaces;

import android.graphics.Bitmap;

import java.util.List;

public interface Interfaces {
    void ApplyTexts(String Title, Boolean required, Bitmap imageView);

    void MultipleApplyTexts(String Title, Boolean required, Bitmap imageView, List<String> list);

    void deleteLister(int title);

}
