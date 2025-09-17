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
@Getter
@Setter
@Entity
@Table(name = "allocation_status")
public class AllocationStatus {
    @Id
    @Column
    private int semester;
    @Column(name = "status_code", nullable = false)
    private int statusCode;
    @Column(nullable = false)
    private int allocatedCount;
    @Column(nullable = false)
    private int unallocatedCount;
}
