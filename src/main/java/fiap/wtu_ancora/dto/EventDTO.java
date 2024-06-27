package fiap.wtu_ancora.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String iframe;
    private Set<UnitDTO> units;
    private Set<UserDTO> users;

    public EventDTO(Long id, String title, String description, LocalDateTime startDate, LocalDateTime endDate, String iframe, Set<UnitDTO> units, Set<UserDTO> users) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.iframe = iframe;
        this.units = units;
        this.users = users;
    }

    public EventDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getIframe() {
        return iframe;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }

    public Set<UnitDTO> getUnits() {
        return units;
    }

    public void setUnits(Set<UnitDTO> units) {
        this.units = units;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }
}
