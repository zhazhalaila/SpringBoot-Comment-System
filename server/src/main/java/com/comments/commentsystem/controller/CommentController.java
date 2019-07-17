package com.comments.commentsystem.controller;


import com.comments.commentsystem.exception.ResourceNotFoundException;
import com.comments.commentsystem.model.Comment;
import com.comments.commentsystem.Custom.CustomCommentResult;
import com.comments.commentsystem.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    /*
    Select all comments
     */
    @GetMapping("/comments")
    @ResponseBody
    public List<Comment> getAllComments(HttpServletResponse response) {
        /*
        Access cors
         */
        response.addHeader("Access-Control-Allow-Origin", "*");
        return commentRepository.findAll();
    }

    /*
    Post a comment
     */
    @PostMapping("/comments")
    @ResponseBody
    public Comment createComment(Comment comment, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return commentRepository.save(comment);
    }

    /*
    Get a comment by id
     */
    @GetMapping("/comments/{id}")
    public Comment getNoteById(@PathVariable(value = "id") Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", commentId));
    }

    /*
    Store html code
     */
    private String html = "";

    @GetMapping("/test")
    @ResponseBody
    public String test(HttpServletResponse response) {
        html = "";
        response.addHeader("Access-Control-Allow-Origin", "*");
        List<Comment> comments = commentRepository.findAll();
        for (Comment c : comments) {
            if (c.getParent_id() == null) {
                html += "<div class='panel panel-primary'><div class='panel-heading'>By <b>"+c.getName()+"</b></div>" +
                        "<div class='panel-body'>" +c.getContent()+ "</div><div class='panel-footer' align='right'>" +
                        "<button type='button' class='btn btn-primary reply' id="+c.getId()+">Reply</button></div> </div>";
                parser(c.getId(), 0, true);
            }
        }
        return html;
    }

    /*
    Recursive to construct html code
     */
    public void parser(Long parent_id, int level, boolean root) {

        /*
        //Recursive print the tree

        String placeholder = " ";
        for (int i=0; i<level; i++) {
            placeholder += " ";
        }
        System.out.println(placeholder+parent_id);

         */

        if (!root) {
            Comment comment = commentRepository.findById(parent_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Note", "id", parent_id));

            html += "<div class='panel panel-primary' style='margin-left:"+ level*48+ "px'>" +
                    "<div class='panel-heading'>By <b>"+comment.getName()+"</b></div>" +
                    "<div class='panel-body'>" +comment.getContent()+ "</div><div class='panel-footer' align='right'>" +
                    "<button type='button' class='btn btn-primary reply' id="+comment.getId()+">Reply</button></div> </div>";

        }

        List<CustomCommentResult> comments = commentRepository.findByParent(parent_id);
        if (!comments.isEmpty()) {
            for (CustomCommentResult comment : comments) {
                parser(comment.getId(), level+1, false);
            }
        }
    }

}
