package com.jsz.peini.ui.fragment.news;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;

/**
 * Created by th on 2017/1/25.
 */
public class PeiniMessage {
    private String form;
    private String to;
    private EMMessage.Type type;
    private EMMessageBody content;

    public PeiniMessage(String form, String to, EMMessage.Type type, EMMessageBody content) {
        this.form = form;
        this.to = to;
        this.type = type;
        this.content = content;
    }

    public PeiniMessage() {
    }

    @Override
    public String toString() {
        return "PeiniMessage{" +
                "form='" + form + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", content=" + content +
                '}';
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public EMMessage.Type getType() {
        return type;
    }

    public void setType(EMMessage.Type type) {
        this.type = type;
    }

    public EMMessageBody getContent() {
        return content;
    }

    public void setContent(EMMessageBody content) {
        this.content = content;
    }
}
