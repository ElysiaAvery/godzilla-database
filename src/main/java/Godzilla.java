import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.lang.Math;

public class Godzilla {
  private int id;
  private String era;
  private String traits;
  private double averageRating;

  public Godzilla(String era, String traits) {
    this.era = era;
    this.traits = traits;
  }

  public int getId() {
    return id;
  }

  public String getEra() {
    return era;
  }

  public String getTraits() {
    return traits;
  }

  public double getAverageRating() {
    return averageRating;
  }

  public static List<Godzilla> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, era, traits FROM godzillas";
      return con.createQuery(sql).executeAndFetch(Godzilla.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO godzillas (era, traits) VALUES (:era, :traits)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("era", this.era)
        .addParameter("traits", this.traits)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherGodzilla) {
    if(!(otherGodzilla instanceof Godzilla)) {
      return false;
    } else {
      Godzilla newGodzilla = (Godzilla) otherGodzilla;
      return this.getEra().equals(newGodzilla.getEra()) &&
             this.getTraits().equals(newGodzilla.getTraits()) &&
             this.getId() == newGodzilla.getId();
    }
  }

  public static Godzilla find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM godzillas WHERE id = :id";
      Godzilla godzilla = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Godzilla.class);
    return godzilla;
    }
  }

  public void updateTraits(String traits) {
    this.traits = traits;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE godzillas SET traits = :traits WHERE id = :id";
      con.createQuery(sql)
        .addParameter("traits", traits)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM godzillas WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public List<Media> getMedias() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM medias WHERE godzillaId = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Media.class);
    }
  }

  public List<Rating> getRatings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM ratings WHERE godzillaId = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Rating.class);
    }
  }

  public List<Comment> getComments() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments WHERE godzillaId = :id";
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
