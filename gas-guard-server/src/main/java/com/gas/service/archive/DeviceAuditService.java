package com.gas.service.archive;

import com.gas.model.AuditingtRequest;
import com.gas.model.BatchIdsRequest;
import com.gas.model.NoPassBatchIdsRequest;

public interface DeviceAuditService {
    void auditing(AuditingtRequest request);

    void passBatchIds(BatchIdsRequest request);

    void noPassBatchIds(NoPassBatchIdsRequest request);
}
