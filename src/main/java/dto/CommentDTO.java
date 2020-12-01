
package dto;

import entities.Comment;
import entities.User;
import java.util.Date;
import java.util.Objects;


public class CommentDTO {
    private String userComment;
    private Comment comment;
    private Date created;
    private User userName;
    private long id;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        this.userComment = comment.getComment();
        this.id = comment.getId();
        this.created = comment.getCreated();
        this.userName = comment.getUser();
        System.out.println("USERNAME: " + userName);
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
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
