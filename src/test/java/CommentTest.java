import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class CommentTest {
  private Comment firstComment;
  private Comment secondComment;

  @Before
  public void initialize() {
    firstComment = new Comment("Loved it!", 1, 1);
    secondComment = new Comment("Hated it!", 1, 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Comment_instantiatesCorrectly_true() {
    assertEquals(true, firstComment instanceof Comment);
  }

  @Test
  public void Comment_instantiatesWithCommentNumber_String() {
    assertEquals("Loved it!", firstComment.getComment());
  }

  @Test
  public void all_returnsAllInstancesOfComment_true() {
    firstComment.save();
    secondComment.save();
    assertTrue(Comment.all().get(0).equals(firstComment));
    assertTrue(Comment.all().get(1).equals(secondComment));
  }

  @Test
  public void getId_tasksInstantiateWithAnId() {
    firstComment.save();
    assertTrue(firstComment.getId() > 0);
  }

  @Test
  public void find_returnsCommentWithSameId_secondComment() {
    firstComment.save();
    secondComment.save();
    assertEquals(Comment.find(secondComment.getId()), secondComment);
  }

  @Test
  public void equals_returnsTrueIfCommentsAreTheSame() {
    Comment myComment = new Comment("Loved it!", 1, 1);
    assertTrue(firstComment.equals(myComment));
  }

  @Test
  public void save_assignsIdToObject() {
    firstComment.save();
    Comment savedComment = Comment.all().get(0);
    assertEquals(firstComment.getId(), savedComment.getId());
  }

  @Test
  public void save_savesGodzillaIdandMediaIdIntoDB_true() {
    Godzilla myGodzilla = new Godzilla("70's", "Mothra");
    myGodzilla.save();
    Media myMedia = new Media("movie", "Godzilla", "Godzilla smashes things", 1);
    myMedia.save();
    Comment myComment = new Comment("Loved it!", myMedia.getId(), myGodzilla.getId());
    myComment.save();
    Comment savedComment = Comment.find(myComment.getId());
    assertEquals(savedComment.getGodzillaId(), myGodzilla.getId());
    assertEquals(savedComment.getMediaId(), myMedia.getId());
  }

  @Test
  public void updateComment_updatesComment_true() {
    firstComment.save();
    firstComment.updateComment("Was so-so.");
    assertEquals("Was so-so.", Comment.find(firstComment.getId()).getComment());
  }

  @Test
  public void delete_deletesComment_true() {
    firstComment.save();
    int firstCommentId = firstComment.getId();
    firstComment.delete();
    assertEquals(null, Comment.find(firstCommentId));
  }
}
