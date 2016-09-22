import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.sql2o.*;

public class Rating {
  private int ratingNumber;
  private int id;
  private int mediaId;
  private int godzillaId;
  private static int[] ratingsArray = new int[] {1,2,3,4,5};

  public Rating(int ratingNumber, int mediaId, int godzillaId) {
    this.ratingNumber = ratingNumber;
    this.mediaId = mediaId;
    this.godzillaId = godzillaId;
  }

  public int getRatingNumber() {
    return ratingNumber;
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

  public static int[] getRatingsArray() {
    return ratingsArray;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ratings(ratingNumber, mediaId, godzillaId) VALUES (:ratingNumber, :mediaId, :godzillaId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("ratingNumber", this.ratingNumber)
        .addParameter("mediaId", this.mediaId)
        .addParameter("godzillaId", this.godzillaId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Rating> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, ratingNumber, mediaId, godzillaId FROM ratings";
      return con.createQuery(sql).executeAndFetch(Rating.class);
    }
  }

  public static Rating find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM ratings WHERE id = :id";
      Rating rating = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Rating.class);
      return rating;
    }
  }

  public void updateRating(int ratingNumber) {
    this.ratingNumber = ratingNumber;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE ratings SET ratingNumber = :ratingNumber WHERE id = :id";
      con.createQuery(sql)
        .addParameter("ratingNumber", ratingNumber)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM ratings WHERE id = :id;";
      con.createQuery(sql)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherRating) {
    if(!(otherRating instanceof Rating)) {
      return false;
    } else {
      Rating newRating = (Rating) otherRating;
      return this.getRatingNumber() == newRating.getRatingNumber() &&
             this.getMediaId() == newRating.getMediaId() &&
             this.getGodzillaId() == newRating.getGodzillaId() &&
             this.getId() == newRating.getId();
    }
  }
}
