import com.zebrunner.carina.core.IAbstractTest;
import jsonPlaceholder.api.DeleteCommentMethod;
import jsonPlaceholder.api.GetCommentMethod;
import jsonPlaceholder.api.PostCommentMethod;
import org.testng.annotations.Test;

public class JsonPlaceholderApiTests implements IAbstractTest {
    @Test()
    public void testGetAllComment() {
        GetCommentMethod getComment = new GetCommentMethod();

        getComment.callAPIExpectSuccess();
        getComment.validateResponseAgainstSchema("api/comments/_get/rs.schema");
    }

    @Test()
    public void testPostComment() {
        PostCommentMethod postComment = new PostCommentMethod();
        postComment.callAPIExpectSuccess();
        postComment.validateResponse();
    }

    @Test()
    public void testGetComment() {
        GetCommentMethod getComment = new GetCommentMethod("501");
        getComment.callAPIExpectSuccess();
        getComment.validateResponseAgainstSchema("api/comments/_get/rs.schema");
    }

    @Test()
    public void testDeleteComment() {
        DeleteCommentMethod deleteComment = new DeleteCommentMethod("501");
        deleteComment.callAPIExpectSuccess();
        deleteComment.validateResponse();
    }
}
