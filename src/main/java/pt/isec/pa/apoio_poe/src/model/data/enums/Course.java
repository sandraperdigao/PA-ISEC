package pt.isec.pa.apoio_poe.src.model.data.enums;

public enum Course {
    LEI("LEI"),
    LEIPL("LEI-PL");

    private final String course;

    Course(final String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return course;
    }

    public static Boolean checkInEnum(String test) {
        for(Course item : Course.values()) {
            if(test.equals(item.course)) {
                return true;
            }
        }
        return false;
    }
}
