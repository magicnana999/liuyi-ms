package com.creolophus.liuyi.common.base;

import java.io.Serializable;

/**
 * @author magicnana
 * @date 2019/6/11 下午4:26
 */
public abstract class AbstractEntity extends AbstractVo {


    public enum State {
        DISABLE(0, "无效"), ENABLE(1, "有效"),
        ;

        int value;
        String desc;

        State(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return this.value;
        }

        public String getDesc() {
            return this.desc;
        }

        public static State valueOf(Integer value) {
            if(value == null) {
                return null;
            }
            for (State state : State.values()) {
                if(state.getValue() == value) {
                    return state;
                }
            }
            return null;
        }
    }

}
