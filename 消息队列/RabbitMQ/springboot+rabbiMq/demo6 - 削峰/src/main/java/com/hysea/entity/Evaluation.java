package com.hysea.entity;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class Evaluation implements Serializable {

    String evaluator;

    String data;

}
