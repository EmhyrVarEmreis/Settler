package pl.morecraft.dev.settler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.mapper.SequenceMapper;
import pl.morecraft.dev.settler.domain.Transaction;

@Service
public class SequenceManager {

    private static final SequenceManagerInner manager = new SequenceManagerInner();

    @Autowired
    public SequenceManager(SequenceMapper sequenceMapper) {
        manager.setSequenceMapper(sequenceMapper);
    }

    public synchronized Long getNextSequenceByName(String sequenceName) {
        manager.incrementSequence(sequenceName, 1L);
        return manager.getSequenceByName(sequenceName);
    }

    public synchronized String getNextReferenceForTransaction(Transaction transaction) {
        String sequenceName = "T" + (transaction.getCategories() == null || transaction.getCategories().isEmpty() ? "RNS" : transaction.getCategories().get(0).getCode());
        Long next = getNextSequenceByName(sequenceName);
        if (next == null) {
            manager.initSequence(sequenceName, 0L);
        }
        return sequenceName + String.format("%04d", next);
    }

    private final static class SequenceManagerInner {

        private SequenceMapper sequenceMapper;

        private synchronized Long getSequenceByName(String sequenceName) {
            return sequenceMapper.getSequenceByName(sequenceName);
        }

        private synchronized void initSequence(String sequenceName, Long initialValue) {
            sequenceMapper.initSequence(sequenceName, initialValue);
        }

        private synchronized void incrementSequence(String sequenceName, Long incrementValue) {
            sequenceMapper.incrementSequence(sequenceName, incrementValue);
        }

        private synchronized void setSequenceMapper(SequenceMapper sequenceMapper) {
            this.sequenceMapper = sequenceMapper;
        }

    }

}
