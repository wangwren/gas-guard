package com.gas.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NoPassBatchIdsRequest implements Serializable {

    private List<Integer> ids;

    private String auditFeedback;
}
