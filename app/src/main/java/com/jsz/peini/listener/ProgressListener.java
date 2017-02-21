package com.jsz.peini.listener;

/**
 * Created by th on 2017/1/9.
 */

public interface ProgressListener {
    void onProgress(long bytesWritten, long contentLength, boolean b);
}
