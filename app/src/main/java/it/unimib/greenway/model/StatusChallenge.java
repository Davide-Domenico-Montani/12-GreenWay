package it.unimib.greenway.model;

public class StatusChallenge {
    int idChallenge;
    double statusChallenge;
    int pointChallenge;
    int percentage;
    boolean completed;


    public StatusChallenge() {
    }

    public StatusChallenge(int idChallenge, double statusChallenge, int pointChallenge, int percentage, boolean completed) {
        this.idChallenge = idChallenge;
        this.statusChallenge = statusChallenge;
        this.pointChallenge = pointChallenge;
        this.percentage = percentage;
        this.completed = completed;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(int idChallenge) {
        this.idChallenge = idChallenge;
    }

    public double getStatusChallenge() {
        return statusChallenge;
    }

    public void setStatusChallenge(double statusChallenge) {
        this.statusChallenge = statusChallenge;
    }

    public int getPointChallenge() {
        return pointChallenge;
    }

    public void setPointChallenge(int pointChallenge) {
        this.pointChallenge = pointChallenge;
    }
}
