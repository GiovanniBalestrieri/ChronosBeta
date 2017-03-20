package box.chronos.userk.chronos.serverRequest;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by francesco on 20/03/2017.
 */

public interface IAsyncResponseWithViews<K>{
    void onRestIntractionResponseWithViews(String message, ImageView imageView, TextView tv_likeCount, K model);
}
