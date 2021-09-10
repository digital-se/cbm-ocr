package com.digitalse.cbm.ocr.TO;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QueueObject {
    private LinkedList<Long> queue;
}
