package application.dto;

public class CrearNovedadResponse {
    private String message;

    public CrearNovedadResponse(double total, String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
