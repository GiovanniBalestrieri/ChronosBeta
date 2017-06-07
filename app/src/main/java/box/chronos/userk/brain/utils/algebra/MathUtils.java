package box.chronos.userk.brain.utils.algebra;

import android.util.Log;

import static box.chronos.userk.brain.utils.AppConstant.FIVE_KM;
import static box.chronos.userk.brain.utils.AppConstant.K_METERS;
import static box.chronos.userk.brain.utils.AppConstant.METERS;
import static box.chronos.userk.brain.utils.AppConstant.MORE_THAN_FIVE_KM;
import static box.chronos.userk.brain.utils.AppConstant.MORE_THAN_TEN_KM;
import static box.chronos.userk.brain.utils.AppConstant.ONE_KM;
import static box.chronos.userk.brain.utils.AppConstant.TEN_KM;

/**
 * Created by userk on 20/05/17.
 */

public class MathUtils {

    public static String fixFloatFormat(String in) {
        return in.replace(',', '.');
    }


    public static String prepareDistanceOffers(String d) {
        String res = "0";
        float distance_km = Float.valueOf(d);
        if ( distance_km > Float.valueOf(FIVE_KM)){
            res = MORE_THAN_FIVE_KM;
        }  else if ( distance_km < Float.valueOf(FIVE_KM) && distance_km > Float.valueOf(ONE_KM)){
            res = String.format("%.0f", distance_km) + K_METERS;
        } else if ( distance_km < Float.valueOf(ONE_KM)){
            //Log.d("AAAA",Float.toString(distance_km*1000.0f));
            res = String.format("%.0f", distance_km*1000.0f) + METERS;
        }
        return res;
    }


    public static String prepareDistanceArticle(String d) {
        String res = "0";
        float distance_km = Float.valueOf(d);
        if ( distance_km > Float.valueOf(TEN_KM)){
            res = MORE_THAN_TEN_KM;
        }  else if ( distance_km < Float.valueOf(TEN_KM) && distance_km > Float.valueOf(ONE_KM)){
            res = String.format("%.00f", distance_km) + K_METERS;
        } else if ( distance_km < Float.valueOf(ONE_KM)){
            //Log.d("AAAA",Float.toString(distance_km*1000.0f));
            res = String.format("%.0f", distance_km*1000.0f) + METERS;
        }
        return res;
    }

}
