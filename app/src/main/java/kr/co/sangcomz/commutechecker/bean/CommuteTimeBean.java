package kr.co.sangcomz.commutechecker.bean;

/**
 * Created by sangc on 2015-09-15.
 */
public class CommuteTimeBean {
    int id;
    int commuteTime;

    public CommuteTimeBean(int id, int commuteTime){
        this.id = id;
        this.commuteTime = commuteTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommuteTime() {
        return commuteTime;
    }

    public void setCommuteTime(int commuteTime) {
        this.commuteTime = commuteTime;
    }
}
