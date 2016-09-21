import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MediaTest {
  private Media firstMedia;
  private Media secondMedia;

  @Before
  public void initialize() {
    firstMedia = new Media("movie", "Mothra vs. Godzilla", "Mothra battles Godzilla", 1);
    secondMedia = new Media("video game", "Godzilla Destroy All Monsters Melee", "Fight all of Godzillas enemies", 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Media_instantiatesCorrectly_true() {
    assertEquals(true, firstMedia instanceof Media);
  }

  @Test
  public void Media_instantiatesWithDescription_String() {
    assertEquals("Mothra battles Godzilla", firstMedia.getDescription());
  }

  @Test
  public void all_returnsAllInstancesOfMedia_true() {
    firstMedia.save();
    secondMedia.save();
    assertTrue(Media.all().get(0).equals(firstMedia));
    assertTrue(Media.all().get(1).equals(secondMedia));
  }

  @Test
  public void getId_tasksInstantiateWithAnId() {
    firstMedia.save();
    assertTrue(firstMedia.getId() > 0);
  }

  @Test
  public void find_returnsMediaWithSameId_secondMedia() {
    firstMedia.save();
    secondMedia.save();
    assertEquals(Media.find(secondMedia.getId()), secondMedia);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Media myMedia = new Media("movie", "Mothra vs. Godzilla", "Mothra battles Godzilla", 1);
    assertTrue(firstMedia.equals(myMedia));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame() {
    firstMedia.save();
    assertTrue(Media.all().get(0).equals(firstMedia));
  }

  @Test
  public void save_assignsIdToObject() {
    firstMedia.save();
    Media savedMedia = Media.all().get(0);
    assertEquals(firstMedia.getId(), savedMedia.getId());
  }

  @Test
  public void save_savesGodzillaIdIntoDB_true() {
    Godzilla myGodzilla = new Godzilla("60's", "Gigan");
    myGodzilla.save();
    Media myMedia = new Media("movie", "Mothra vs. Godzilla", "Mothra battles Godzilla", myGodzilla.getId());
    myMedia.save();
    Media savedMedia = Media.find(myMedia.getId());
    assertEquals(savedMedia.getGodzillaId(), myGodzilla.getId());
  }

  @Test
  public void updateDescription_updatesMediaDescription_true() {
    firstMedia.save();
    firstMedia.updateDescription("Godzilla destroys the city");
    assertEquals("Godzilla destroys the city", Media.find(firstMedia.getId()).getDescription());
  }

  @Test
  public void updateTitle_updatesMediaTitle_true() {
    firstMedia.save();
    firstMedia.updateTitle("Son of Godzilla");
    assertEquals("Son of Godzilla", Media.find(firstMedia.getId()).getTitle());
  }

  @Test
  public void updateComment_updatesMediaComment_true() {
    firstMedia.save();
    firstMedia.updateComment("very bad");
    assertEquals("very bad", Media.find(firstMedia.getId()).getComment());
  }

  @Test
  public void getRatings_retrievesAllRatingsFromDatabase_RatingsList() {
    firstMedia.save();
    Godzilla myGodzilla = new Godzilla("60's", "Hedorah");
    Rating firstRating = new Rating(5, firstMedia.getId(), myGodzilla.getId());
    firstRating.save();
    Rating secondRating = new Rating(5, firstMedia.getId(), myGodzilla.getId());
    secondRating.save();
    Rating[] ratings = new Rating[] { firstRating, secondRating };
    assertTrue(firstMedia.getRatings().containsAll(Arrays.asList(ratings)));
  }

  @Test
  public void delete_deletesMedia_true() {
    firstMedia.save();
    int firstMediaId = firstMedia.getId();
    firstMedia.delete();
    assertEquals(null, Media.find(firstMediaId));
  }
}
