package fiap.wtu_ancora.dto;

public class UnitDTO {


    public UnitDTO(Long id) {
        this.id = id;
    }

    public UnitDTO() {
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}