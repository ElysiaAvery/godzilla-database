import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.lang.Math;

public class Media {
  private String type;
  private String title;
  private String description;
  private int id;
  private int godzillaId;
  private double averageRating;

  public Media(String type, String title, String description, int godzillaId) {
    this.type = type;
    this.title = title;
    this.description = description;
    this.godzillaId = godzillaId;
  }

  public String getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public int getGodzillaId() {
    return godzillaId;
  }

  public double getAverageRating() {
    return averageRating;
  }

  @Override
  public boolean equals(Object otherMedia) {
    if(!(otherMedia instanceof Media)) {
      return false;
    } else {
      Media newMedia = (Media) otherMedia;
      return this.getType().equals(newMedia.getType()) &&
             this.getDescription().equals(newMedia.getDescription()) && this.getTitle().equals(newMedia.getTitle()) &&
             this.getId() == newMedia.getId() &&
             this.getGodzillaId() == newMedia.getGodzillaId();
    }
  }
    public static List<Media> all() {
      String sql = "SELECT id, type, title, description, godzillaId FROM medias";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Media.class);
      }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO medias (type, title, description, godzillaId) VALUES (:type, :title, :description, :godzillaId)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("type", this.type)
          .addParameter("title", this.title)
          .addParameter("description", this.description)
          .addParameter("godzillaId", this.godzillaId)
          .executeUpdate()
          .getKey();
      }
    }

    public static Media find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM medias where id=:id";
        Media media = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Media.class);
      return media;
      }
    }

    public void updateDescription(String description) {
      this.description = description;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE medias SET description = :description WHERE id = :id";
        con.createQuery(sql)
          .addParameter("description", description)
          .addParameter("id", id)
          .executeUpdate();
      }
    }

    public void updateTitle(String title) {
      this.title = title;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE medias SET title = :title WHERE id = :id";
        con.createQuery(sql)
          .addParameter("title", title)
          .addParameter("id", id)
          .executeUpdate();
      }
    }

    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM medias WHERE id = :id;";
        con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    }

    public List<Rating> getRatings() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM ratings where mediaId = :id";
        return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Rating.class);
      }
    }

    public List<Comment> getComments() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM comments where mediaId = :id";
        return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Comment.class);
      }
    }

    public void calculateAverage() {
      int total = 0;
      if(this.getRatings().size() > 0) {
        for (Rating rating : this.getRatings()) {
          total += rating.getRatingNumber();
        }
        this.averageRating = total/this.getRatings().size();
      } else {
        this.averageRating = 0;
      }
    }
}
