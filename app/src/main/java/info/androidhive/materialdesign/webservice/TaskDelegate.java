package info.androidhive.materialdesign.webservice;

import org.json.JSONException;

/**
 * Created by evs on 12/20/2016.
 */

public interface TaskDelegate {
    void TaskCompletionResult(String result) throws JSONException;
}
