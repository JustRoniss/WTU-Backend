package fiap.wtu_ancora.model;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "unidades")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String endereco;
    private boolean isFranchised;

    @OneToMany(mappedBy = "unit")
    private Set<Event> events;

    public Unit(Long id, String name, String endereco, boolean isFranchised) {
        this.id = id;
        this.name = name;
        this.endereco = endereco;
        this.isFranchised = isFranchised;
    }

    public Unit() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isFranchised() {
        return isFranchised;
    }

    public void setFranchised(boolean franchised) {
        isFranchised = franchised;
    }

}
