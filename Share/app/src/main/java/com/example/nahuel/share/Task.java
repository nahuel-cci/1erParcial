package com.example.nahuel.share;

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
    private Integer mExecutiontime;
    private Integer mDuration;

    public Task() {}  // Needed for Firebase

//    public Task(String name, Integer creationtime, Integer executiontime, Integer duration, String state) {
    public Task(String name, Long creationtime, String state) {
        mName = name;
        mCreationtime = creationtime;
//        mExecutionTime = executiontime;
//        mDuration = duration;
        mState = state;
    }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public Long getCreationtime() { return mCreationtime;}

    public void setCreationtime(Long creationtime) { mCreationtime = creationtime;}

    public Integer getExecutionTime () {return mExecutiontime;}

    public void setExecutionTime (Integer executiontime) { mExecutiontime = executiontime;}

    public Integer getDuration () { return mDuration;}

    public void setDuration (Integer duration) { mDuration = duration;}

    public String getState () { return mState;}

    public void setState (String state) {mState = state;}


}
