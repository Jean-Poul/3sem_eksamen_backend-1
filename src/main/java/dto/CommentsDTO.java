package dto;

import entities.Comment;
import java.util.ArrayList;
import java.util.List;

public class CommentsDTO {

    List<CommentDTO> all = new ArrayList();

    public CommentsDTO(List<Comment> commentEntities) {
        commentEntities.forEach((c) -> {
            all.add(new CommentDTO(c));
        });
    }

    public List<CommentDTO> getAll() {
        return all;
    }

    public void setAll(List<CommentDTO> all) {
        this.all = all;
    }

}
