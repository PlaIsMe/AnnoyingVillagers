package com.pla.annoyinglib.rig;

public interface LockableRigAttackAnimation extends RigStunnableEntity {
    void lock();
    void unlock();
    boolean isLocked();
}
