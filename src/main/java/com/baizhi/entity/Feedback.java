package com.baizhi.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (YxFeedback)实体类
 *
 * @author makejava
 * @since 2020-07-06 22:46:29
 */
public class Feedback implements Serializable {
    private static final long serialVersionUID = -48524600606655446L;

    private String id;

    private String userId;

    private Date feedbackDate;

    private String title;

    private String content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}