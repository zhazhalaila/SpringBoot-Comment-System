package com.comments.commentsystem.Custom;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomCommentResult {
    /*
    Custom a CustomCommentResult class to select child nodes
    This class filed should to be json
    Sql statement:
        "SELECT NEW  com.comments.commentsystem.Custom.CustomCommentResult(c.id, c.name, c.content, c.parent_id)" +
            "FROM Comment as c WHERE parent_id = :parent_id"
     */
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("content")
    private String content;
    @JsonProperty("parent_id")
    private Long parent_id;

    public CustomCommentResult(Long id, String name, String content, Long parent_id) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }
}
