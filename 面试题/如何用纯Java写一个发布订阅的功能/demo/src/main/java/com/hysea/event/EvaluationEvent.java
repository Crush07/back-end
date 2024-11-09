package com.hysea.event;


import java.util.EventObject;

public class EvaluationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EvaluationEvent(Object source) {
        super(source);
    }
}
