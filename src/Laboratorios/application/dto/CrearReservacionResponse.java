package application.dto;

public class CrearReservacionResponse {
    private String message;
    private double total;

    public CrearReservacionResponse(double total, String message) {
        this.total = total;
        this.message = message;
    }

    public double getTotal() {
        return total;
    }
    public String getMessage() {
        return message;
    }
}
