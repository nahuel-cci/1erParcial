package com.example.nahuel.share;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nahuel on 06/12/2017.
 */

/* Ejemple de nodo en l RTDB
"task1": {
        "name": "on",
        "creationtime": 1512306000,
        "executiontime": epoch_task1,
        "duration": 15 #in seconds
        }
*/

public class Task {
    private String mState;
    private String mName;
    private Long mCreationtime;
    private Long mDuration;
    private Long mProgress;

    public Task() {}  // Needed for Firebase

//    public Task(String name, Integer creationtime, Integer executiontime, Integer duration, String state) {
    public Task(String name, Long creationtime, String state, Long duration, Long progress) {
        mName = name;
        mCreationtime = creationtime;
        mProgress = progress;
        mDuration = duration;
        mState = state;
    }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public Long getCreationtime() { return mCreationtime;}

    public void setCreationtime(Long creationtime) { mCreationtime = creationtime;}

    public Long getDuration () { return mDuration;}

    public void setDuration (Long duration) { mDuration = duration;}

    public String getState () { return mState;}

    public void setState (String state) {mState = state;}

    public void setProgress (Long progress){ mProgress = progress;}

    public Long getProgress () {return mProgress;}


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("name", mName);
        result.put("creationtime", mCreationtime);
        result.put("progress", mProgress);
        result.put("duration", mDuration);
        result.put("state", mState);

        return result;
    }


}
