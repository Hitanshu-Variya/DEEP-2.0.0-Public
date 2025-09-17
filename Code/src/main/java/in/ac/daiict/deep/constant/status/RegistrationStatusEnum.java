package in.ac.daiict.deep.constant.status;

public enum RegistrationStatusEnum {
    open,close,never;

    @Override
    public String toString() {
        return name();
    }
}
