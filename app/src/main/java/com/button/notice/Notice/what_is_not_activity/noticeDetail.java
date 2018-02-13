package com.button.notice.Notice.what_is_not_activity;

/**
 * Created by Jill on 2017/12/14.
 *
 * 定义一个实体类Notice_detial
 */

public class noticeDetail {
    //变量们
    private String title;
    private String release_date;
    private String label;
    private String publisher;
    private  String deadline_date;

    //一个通知
    public noticeDetail(String title, String release_date,
                        String label, String publisher, String deadline_date)
    {
        this.title = title;
        this.release_date = release_date;
        this.label = label;
        this.publisher = publisher;
        this.deadline_date = deadline_date;
    }

    public String getTitle(){
        return title;
    }

    public String getRelease_date(){
        return release_date;
    }

    public String getLabel(){
        return label;
    }

    public String getPublisher(){
        return publisher;
    }

    public String getDeadline_date(){
        return deadline_date;
    }



}
