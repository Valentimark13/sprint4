package dto;

public class DeliveryDetailsForm {
    public final String dueDate;

    public final String duration;

    public final String color;

    public final String comment;

    public DeliveryDetailsForm(String dueDate, String duration, String color, String comment) {
        this.dueDate = dueDate;
        this.duration = duration;
        this.color = color;
        this.comment = comment;
    }
}
