package ua.des.kino.model.mysql.submodel;

public enum Quality {
    DD("2D"),
    DDD("3D"),
    IMAX("IMAX");

    private final String text;

    Quality(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
