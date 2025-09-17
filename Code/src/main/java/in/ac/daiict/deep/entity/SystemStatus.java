package in.ac.daiict.deep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "system_status")
public class SystemStatus {
    @Id
    @Column(name = "status_name", length = 200)
    private String statusName;

    @Column(name = "status_value", length = 100)
    private String statusValue;
}
