import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Comment {
  private String comment;
  private int id;
  private int mediaId;
  private int godzillaId;

  public Comment(String comment, int mediaId, int godzillaId) {
    this.comment = comment;
    this.mediaId = mediaId;
    this.godzillaId = godzillaId;
  }

  public String getComment() {
    return comment;
  }

  public int getId() {
    return id;
  }

  public int getMediaId() {
    return mediaId;
  }

  public int getGodzillaId() {
    return godzillaId;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO comments(comment, mediaId, godzillaId) VALUES (:comment, :mediaId, :godzillaId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("comment", this.comment)
        .addParameter("mediaId", this.mediaId)
        .addParameter("godzillaId", this.godzillaId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Comment> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, comment, mediaId, godzillaId FROM comments";
      return con.createQuery(sql).executeAndFetch(Comment.class);
    }
  }

  public static Comment find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments WHERE id = :id";
      Comment comment = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Comment.class);
      return comment;
    }
  }

  public void updateComment(String comment) {
    this.comment = comment;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE comments SET comment = :comment WHERE id = :id";
      con.createQuery(sql)
        .addParameter("comment", comment)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM comments WHERE id = :id;";
      con.createQuery(sql)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherComment) {
    if(!(otherComment instanceof Comment)) {
      return false;
    } else {
      Comment newComment = (Comment) otherComment;
      return this.getComment().equals(newComment.getComment()) &&
             this.getMediaId() == newComment.getMediaId() &&
             this.getGodzillaId() == newComment.getGodzillaId() &&
             this.getId() == newComment.getId();
    }
  }
}
