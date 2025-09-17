package in.ac.daiict.deep.constant.status;

public enum ResultStatusEnum {
    pending,declared;

    @Override
    public String toString() {
        return name();
    }
}