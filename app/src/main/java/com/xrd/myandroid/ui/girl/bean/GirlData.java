package com.xrd.myandroid.ui.girl.bean;

import java.util.List;

/**
 * Created by user on 2018/9/21.
 */

public class GirlData {
    private boolean isError;
    private List<PhotoGirl> results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setResults(List<PhotoGirl> results) {
        this.results = results;
    }

    public List<PhotoGirl> getResults() {
        return results;
    }
}
