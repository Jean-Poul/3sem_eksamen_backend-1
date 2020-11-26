
package dto;

import entities.Comment;


public class CommentDTO {
    private String userComment;
    private Comment comment;
    private long id;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        this.userComment = comment.getComment();
        this.id = comment.getId();
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }    

    public long getId() {
        return id;
    }   
    
    
}
