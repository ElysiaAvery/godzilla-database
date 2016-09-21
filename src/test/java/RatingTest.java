import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RatingTest {
  private Rating firstRating;
  private Rating secondRating;

  @Before
  public void initialize() {
    firstRating = new Rating(5, 1, 1);
    secondRating = new Rating(4, 1, 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Rating_instantiatesCorrectly_true() {
    assertEquals(true, firstRating instanceof Rating);
  }

  @Test
  public void Rating_instantiatesWithRatingNumber_String() {
    assertEquals(5, firstRating.getRatingNumber());
  }

  @Test
  public void all_returnsAllInstancesOfRating_true() {
    firstRating.save();
    secondRating.save();
    assertTrue(Rating.all().get(0).equals(firstRating));
    assertTrue(Rating.all().get(1).equals(secondRating));
  }

  @Test
  public void getId_tasksInstantiateWithAnId() {
    firstRating.save();
    assertTrue(firstRating.getId() > 0);
  }

  @Test
  public void find_returnsRatingWithSameId_secondRating() {
    firstRating.save();
    secondRating.save();
    assertEquals(Rating.find(secondRating.getId()), secondRating);
  }

  @Test
  public void equals_returnsTrueIfRatingNumbersAreTheSame() {
    Rating myRating = new Rating(5, 1, 1);
    assertTrue(firstRating.equals(myRating));
  }

  @Test
  public void save_returnsTrueIfRatingNumbersAreTheSame() {
    firstRating.save();
    assertTrue(Rating.all().get(0).equals(firstRating));
  }

  @Test
  public void save_assignsIdToObject() {
    firstRating.save();
    Rating savedRating = Rating.all().get(0);
    assertEquals(firstRating.getId(), savedRating.getId());
  }

  @Test
  public void save_savesGodzillaIdandMediaIdIntoDB_true() {
    Godzilla myGodzilla = new Godzilla("70's", "Mothra");
    myGodzilla.save();
    Media myMedia = new Media("movie", "Godzilla", "Godzilla smashes things", 1);
    myMedia.save();
    Rating myRating = new Rating(5, myMedia.getId(), myGodzilla.getId());
    myRating.save();
    Rating savedRating = Rating.find(myRating.getId());
    assertEquals(savedRating.getGodzillaId(), myGodzilla.getId());
    assertEquals(savedRating.getMediaId(), myMedia.getId());
  }

  @Test
  public void update_updatesRatingNumber_true() {
    firstRating.save();
    firstRating.update(4);
    assertEquals(4, Rating.find(firstRating.getId()).getRatingNumber());
  }

  @Test
  public void delete_deletesRating_true() {
    firstRating.save();
    int firstRatingId = firstRating.getId();
    firstRating.delete();
    assertEquals(null, Rating.find(firstRatingId));
  }
}
