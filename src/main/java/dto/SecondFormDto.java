package dto;

public class SecondFormDto {
    public final String dueDate;

    public final String duration;

    public final String color;

    public final String comment;

    public SecondFormDto(String dueDate, String duration, String color, String comment) {
        this.dueDate = dueDate;
        this.duration = duration;
        this.color = color;
        this.comment = comment;
    }
}
