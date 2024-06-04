package fiap.wtu_ancora.dto;

public class UnitDTO {

    private Long id;

    private String name;

    public UnitDTO(Long id) {
        this.id = id;
    }

    public UnitDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}