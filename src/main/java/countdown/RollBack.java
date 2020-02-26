package countdown;

/**
 * @Author: zhangsh
 * @Date: 2020/2/24 16:34
 * @Version 1.0
 * Description
 */
public class RollBack {
    private Boolean isRollback;

    public RollBack(Boolean isRollback) {
        this.isRollback = isRollback;
    }

    public Boolean getRollback() {
        return isRollback;
    }

    public void setRollback(Boolean rollback) {
        isRollback = rollback;
    }
}
