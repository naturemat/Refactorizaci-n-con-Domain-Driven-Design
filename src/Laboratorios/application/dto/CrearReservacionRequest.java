package application.dto;

public class CrearReservacionRequest {
    private Long userId;
    private Long roomId;
    private Integer nights;

    public CrearReservacionRequest(Long userId, Long roomId, Integer nights) {
        this.userId = userId;
        this.roomId = roomId;
        this.nights = nights;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Integer getNights() {
        return nights;
    }
}
