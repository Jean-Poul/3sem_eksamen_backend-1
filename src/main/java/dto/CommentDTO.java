
package dto;

import entities.Comment;
import java.util.Objects;


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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.userComment);
        hash = 79 * hash + Objects.hashCode(this.comment);
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommentDTO other = (CommentDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.userComment, other.userComment)) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        return true;
    }
    
    
}
