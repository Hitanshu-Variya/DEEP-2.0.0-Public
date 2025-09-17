package in.ac.daiict.deep.entity;

import in.ac.daiict.deep.constant.database.DBConstants;
import in.ac.daiict.deep.entity.compositekeys.AllocationReportPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = DBConstants.ALLOCATION_REPORT_TABLE)
@IdClass(AllocationReportPK.class)
public class AllocationReport {
    @Id
    @Column(length = 100)
    private String name;
    @Id
    private int semester;
    @Column(columnDefinition = "BYTEA")
    private byte[] file;
}
