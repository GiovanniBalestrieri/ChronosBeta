package box.chronos.userk.brain.callbacks;

/**
 * Created by francesco on 20/03/2017.
 */

public interface IAsyncResponse {

    /******* Response returned By Network Call *******/
    void onRestInteractionResponse(String response);


    /******* Error Response returned By Network Call *******/
    void onRestInteractionError(String message);

}
