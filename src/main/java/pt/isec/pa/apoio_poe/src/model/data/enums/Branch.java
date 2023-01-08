package pt.isec.pa.apoio_poe.src.model.data.enums;

public enum Branch {
    RAS("RAS"),
    SI("SI"),
    DA("DA");

    private final String branch;

    Branch(final String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return branch;
    }

    public static Boolean checkInEnum(String test) {
        for(Branch item : Branch.values()) {
            if(test.equals(item.branch)) {
                return true;
            }
        }
        return false;
    }
}
