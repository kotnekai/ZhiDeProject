package com.zhide.app.model;

import org.jaaksi.pickerview.dataset.OptionDataSet;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hasee on 2017/12/30.
 */

public class SpinnerSelectModel implements Serializable,OptionDataSet {
    private long id;
    private String name;

    public SpinnerSelectModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<? extends OptionDataSet> getSubs() {
        return null;
    }

    @Override
    public String getValue() {
        return String.valueOf(id);
    }

    @Override
    public CharSequence getCharSequence() {
        return name;
    }
}
