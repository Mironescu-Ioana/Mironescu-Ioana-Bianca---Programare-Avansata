package org.example;

public interface Agent {
    String getId();
    void setSleepTime(int ms);
    int getSleepTime();
    void setPaused(boolean paused);
}
