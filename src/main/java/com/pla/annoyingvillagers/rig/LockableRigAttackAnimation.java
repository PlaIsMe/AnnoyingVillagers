package com.pla.annoyingvillagers.rig;

public interface LockableRigAttackAnimation extends RigStunnableEntity {
    void lock();
    void unlock();
    boolean isLocked();
}
