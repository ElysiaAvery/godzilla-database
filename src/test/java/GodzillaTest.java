import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GodzillaTest {
  private Godzilla firstGodzilla;
  private Godzilla secondGodzilla;

  @Before
  public void initialize() {
    firstGodzilla = new Godzilla("60's", "Hedorah");
    secondGodzilla = new Godzilla("60's", "Hedorah");
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Godzilla_instantiatesCorrectly_true() {
    assertEquals(true, firstGodzilla instanceof Godzilla);
  }

  @Test
  public void Godzilla_instantiatesWithEra_String() {
    assertEquals("60's", firstGodzilla.getEra());
  }

  @Test
  public void all_returnsAllInstancesOfGodzilla_true() {
    firstGodzilla.save();
    secondGodzilla.save();
    assertTrue(Godzilla.all().get(0).equals(firstGodzilla));
    assertTrue(Godzilla.all().get(1).equals(secondGodzilla));
  }

  @Test
  public void getId_tasksInstantiateWithAnId() {
    firstGodzilla.save();
    assertTrue(firstGodzilla.getId() > 0);
  }

  @Test
  public void find_returnsGodzillaWithSameId_secondGodzilla() {
    firstGodzilla.save();
    secondGodzilla.save();
    assertEquals(Godzilla.find(secondGodzilla.getId()), secondGodzilla);
  }

  @Test
  public void equals_returnsTrueIfErasAreTheSame() {
    assertTrue(firstGodzilla.equals(secondGodzilla));
  }

  @Test
  public void save_returnsTrueIfErasAreTheSame() {
    firstGodzilla.save();
    assertTrue(Godzilla.all().get(0).equals(firstGodzilla));
  }

  @Test
  public void save_assignsIdToObject() {
    firstGodzilla.save();
    Godzilla savedGodzilla = Godzilla.all().get(0);
    assertEquals(firstGodzilla.getId(), savedGodzilla.getId());
  }

  @Test
  public void save_savesGodzillaIdIntoDB_true() {
    firstGodzilla.save();
    Godzilla savedGodzilla = Godzilla.find(firstGodzilla.getId());
    assertEquals(savedGodzilla.getId(), firstGodzilla.getId());
  }

  @Test
  public void updateEnemies_updatesGodzillaEnemies_true() {
    firstGodzilla.save();
    firstGodzilla.updateEnemies("Mothra");
    assertEquals("Mothra", Godzilla.find(firstGodzilla.getId()).getEnemies());
  }

  @Test
  public void getRatings_retrievesAllRatingsFromDatabase_RatingsList() {
    firstGodzilla.save();
    Media myMedia = new Media("movie", "Hedorah Vs Godzilla", "Hedorah represents the world's pollution in a psychedelic 70's film", firstGodzilla.getId());
    myMedia.save();
    Rating firstRating = new Rating(5, myMedia.getId(), firstGodzilla.getId());
    firstRating.save();
    Rating secondRating = new Rating(5, myMedia.getId(), firstGodzilla.getId());
    secondRating.save();
    Rating[] ratings = new Rating[] { firstRating, secondRating };
    assertTrue(firstGodzilla.getRatings().containsAll(Arrays.asList(ratings)));
  }

  @Test
  public void getMedias_retrievesAllMediasFromDatabase_MediasList() {
    firstGodzilla.save();
    Media firstMedia = new Media("movie", "Hedorah Vs Godzilla", "Hedorah represents the world's pollution in a psychedelic 70's film", firstGodzilla.getId());
    firstMedia.save();
    Media secondMedia = new Media("movie", "Hedorah Vs Godzilla", "Hedorah represents the world's pollution in a psychedelic 70's film", firstGodzilla.getId());
    secondMedia.save();
    Media[] medias = new Media[] { firstMedia, secondMedia };
    assertTrue(firstGodzilla.getMedias().containsAll(Arrays.asList(medias)));
  }

  @Test
  public void delete_deletesGodzilla_true() {
    firstGodzilla.save();
    int firstGodzillaId = firstGodzilla.getId();
    firstGodzilla.delete();
    assertEquals(null, Godzilla.find(firstGodzillaId));
  }
}
