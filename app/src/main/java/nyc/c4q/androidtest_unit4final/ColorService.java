package nyc.c4q.androidtest_unit4final;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by c4q on 1/10/18.
 */

public interface ColorService {
    @GET("operable/cog/master/priv/css-color-names.json")
    Call<ColorModel> getModel();
}
