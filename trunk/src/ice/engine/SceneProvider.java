package ice.engine;

import android.util.Log;

public abstract class SceneProvider {

    protected SceneProvider() {
        tag = getClass().getSimpleName();
    }

    protected void onCreate() {
        Log.i(tag, "onCreate");
    }

    protected void onResume() {
        Log.i(tag, "onResume");
    }

    protected void onPause() {
        Log.i(tag, "onPause");
    }

    protected void onStop() {
        Log.i(tag, "onStop");
    }

    protected abstract Scene getScene();

    protected boolean isEntry() {
        return false;
    }

    protected boolean onBackPressed() {
        return false;
    }

    private final String tag;
}
