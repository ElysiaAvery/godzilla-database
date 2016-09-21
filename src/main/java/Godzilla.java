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

  @Override
  public boolean equals(Object otherGodzilla) {
    if(!(otherGodzilla instanceof Godzilla)) {
      return false;
    } else {
      Godzilla newGodzilla = (Godzilla) otherGodzilla;
      return this.getEra().equals(newGodzilla.getEra()) &&
             this.getEnemies().equals(newGodzilla.getEnemies()) &&
             this.getId() == newGodzilla.getId() &&
             this.getComment().equals(newGodzilla.getComment());
    }
  }
}
