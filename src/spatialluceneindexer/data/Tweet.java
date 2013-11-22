/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spatialluceneindexer.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 *
 * @author spousty
 * 
 * Basically just a POJO to bind to the JSON
 */
public class Tweet {

    //need this because the JSON actually contains a capitol letter
    //@JsonProperty("Name")
    private String favorited="";
    private String truncated="";
    private String text="";
    private String created_at="";
    private String source="";
    private String in_reply_to_status_id="";
    private String in_reply_to_screen_name="";
    private String user="";
    private String id="";
    private String in_reply_to_user_id="";

    @Override
    public String toString() {
        return "Tweet{" + "tweet=" + text + ", user=" + user + ", tweet_id=" + id + '}';
    }

	public String getFavorited() {
		return favorited;
	}

	public void setFavorited(String favorited) {
		this.favorited = favorited;
	}

	public String getTruncated() {
		return truncated;
	}

	public void setTruncated(String truncated) {
		this.truncated = truncated;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIn_reply_to_status_id() {
		return in_reply_to_status_id;
	}

	public void setIn_reply_to_status_id(String in_reply_to_status_id) {
		this.in_reply_to_status_id = in_reply_to_status_id;
	}

	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIn_reply_to_user_id() {
		return in_reply_to_user_id;
	}

	public void setIn_reply_to_user_id(String in_reply_to_user_id) {
		this.in_reply_to_user_id = in_reply_to_user_id;
	}   
    
}
