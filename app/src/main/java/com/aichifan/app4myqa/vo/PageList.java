package com.aichifan.app4myqa.vo;

import com.aichifan.app4myqa.pojo.Question;

import java.util.List;

/**
 * Created by michael on 2016/9/1.
 */
public class PageList
{
    private Integer draw;

    private Integer recordsTotal;

    private Integer recordsFiltered;

    private List<Question> data;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

}
