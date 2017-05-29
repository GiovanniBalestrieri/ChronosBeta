package box.chronos.userk.brain.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by ChronosTeam on 27/02/2017.
 */

public class VideoUtility {

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(int dp, Context ctx) {
        Resources r = ctx.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}