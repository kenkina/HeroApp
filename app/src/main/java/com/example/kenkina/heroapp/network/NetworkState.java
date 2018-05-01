package com.example.kenkina.heroapp.network;


public class NetworkState {
    private final Status status;
    private final String message;

    public static final NetworkState LOADED;
    public static final NetworkState LOADING;

    public NetworkState(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    static {
        LOADED = new NetworkState(Status.SUCCESS, "Success");
        LOADING = new NetworkState(Status.RUNNING, "Running");
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
