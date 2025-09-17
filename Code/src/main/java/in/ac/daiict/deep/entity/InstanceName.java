package in.ac.daiict.deep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "instance_names")
public class InstanceName implements Serializable {
    @Id
    @Column(name = "instance_name",length = 20)
    private String instanceName;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Serial
    private static final long serialVersionUID = 1L;

    public InstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
