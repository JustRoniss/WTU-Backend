package fiap.wtu_ancora.model.dto;

import java.util.Date;
import java.util.Set;

public class EventDTO {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Set<Long> unitIds;
    private Set<String> userEmails;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Long> getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(Set<Long> unitIds) {
        this.unitIds = unitIds;
    }

    public Set<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(Set<String> userEmails) {
        this.userEmails = userEmails;
    }
}
