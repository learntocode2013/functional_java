package org.learn.functional.java;

public class Order {
    private final String _trackingId;
    private final Customer _from;
    private final float _value;

    public Order(String _trackingId, Customer _from, float _value) {
        this._trackingId = _trackingId;
        this._from = _from;
        this._value = _value;
    }

    public String getTrackingId() {
        return _trackingId;
    }

    public Customer getFrom() {
        return _from;
    }

    public float getValue() {
        return _value;
    }
}
