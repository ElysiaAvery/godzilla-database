import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Godzilla {
  private int id;
  private String era;
  private String enemies;
  private String comment;

  public Godzilla(String era, String enemies) {
    this.id = id;
    this.era = era;
    this.enemies = enemies;
    this.comment = comment;
  }

  public int getId() {
    return id;
  }

  public String getEra() {
    return era;
  }

  public String getEnemies() {
    return enemies;
  }

  public String getComment() {
    return comment;
  }

  public static List<Godzilla> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, era, enemies FROM godzillas";
      return con.createQuery(sql).executeAndFetch(Godzilla.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO godzillas (era, enemies) VALUES (:era, :enemies)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("era", this.era)
        .addParameter("enemies", this.enemies)
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
             this.getEnemies().equals(newGodzilla.getEnemies()) &&
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

  public void updateEnemies(String enemies) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE godzillas SET enemies = :enemies WHERE id = :id";
      con.createQuery(sql)
        .addParameter("enemies", enemies)
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
}
