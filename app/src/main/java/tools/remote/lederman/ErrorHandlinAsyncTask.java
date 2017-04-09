package tools.remote.lederman;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by Georg on 01.04.2017.
 */

abstract class ErrorHandlingAsyncTask<X, Y, Z> extends AsyncTask<X, Y, Z> {

private Exception exception = null;

protected abstract void onResult(Z result);

protected abstract void onException(Exception e);

protected abstract Z tryInBackground(X... params) throws Exception;

    @Override
    final protected void onPostExecute(Z result) {
        if(exception == null) {
            onResult(result);
        } else {
            onException(exception);
        }
    }

    @Override
    final protected Z doInBackground(X... params) {
        try {
            return tryInBackground(params);
        } catch(Exception e) {
            exception = e;
        }
        return null;
    }
}
