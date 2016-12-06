package com.soubu.goldensteward.support.widget.linebarchart;


/**
 * Created by Dacer on 11/14/13.
 */
public class PieHelper {

    private float startDegree;
    private float endDegree;
    private float targetStartDegree;
    private float targetEndDegree;
    private String title;
    private int color;
    private float sweepDegree;
    private int type;

    public static final int TYPE_SMALL = 0x00;
    public static final int TYPE_NORMAL = 0x01;
    public static final int TYPE_BIG = 0x02;


    int velocity = 5;

    /**
     * @param percent from 0 to 100
     */
    public PieHelper(float percent, int type) {
        this(percent, null, 0, type);
    }

    public PieHelper(float percent, int color, int type) {
        this(percent, null, color, type);
    }

    /**
     * @param percent from 0 to 100
     * @param title
     */
    PieHelper(float percent, String title) {
        this(percent, title, 0, 0);
    }

    /**
     * @param percent from 0 to 100
     * @param title
     * @param color
     */
    PieHelper(float percent, String title, int color, int type) {
        this.sweepDegree = percent * 360 / 100;
        this.title = title;
        this.color = color;
        this.type = type;
    }


    PieHelper(float startDegree, float endDegree, PieHelper targetPie) {
        this.startDegree = startDegree;
        this.endDegree = endDegree;
        targetStartDegree = targetPie.getStartDegree();
        targetEndDegree = targetPie.getEndDegree();
        this.sweepDegree = targetPie.getSweep();
        this.title = targetPie.getTitle();
        this.color = targetPie.getColor();
        this.type = targetPie.getType();
    }

    PieHelper setTarget(PieHelper targetPie) {
        this.targetStartDegree = targetPie.getStartDegree();
        this.targetEndDegree = targetPie.getEndDegree();
        this.title = targetPie.getTitle();
        this.color = targetPie.getColor();
        this.sweepDegree = targetPie.getSweep();
        return this;
    }

    void setDegree(float startDegree, float endDegree) {
        this.startDegree = startDegree;
        this.endDegree = endDegree;
    }

    boolean isColorSetted() {
        return color != 0;
    }

    boolean isAtRest() {
        return (startDegree == targetStartDegree) && (endDegree == targetEndDegree);
    }

    void update() {
        this.startDegree = updateSelf(startDegree, targetStartDegree, velocity);
        this.endDegree = updateSelf(endDegree, targetEndDegree, velocity);
        this.sweepDegree = endDegree - startDegree;
    }

    String getPercentStr() {
        float percent = sweepDegree / 360 * 100;
        return String.valueOf((int) percent) + "%";
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public float getSweep() {
        return sweepDegree;
    }

    public float getStartDegree() {
        return startDegree;
    }

    public float getEndDegree() {
        return endDegree;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private float updateSelf(float origin, float target, int velocity) {
        if (origin < target) {
            origin += velocity;
        } else if (origin > target) {
            origin -= velocity;
        }
        if (Math.abs(target - origin) < velocity) {
            origin = target;
        }
        return origin;
    }
}
