package in.ac.daiict.deep.util.allocation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class AllocationStudent {
    private String sid;
    private String name;
    private String program;
    private int semester;
    private Map<String, Integer> requirements; // category as key and required number of it as value
    private Set<String> allocatedCourses;
    private Set<String> allocatedSlots;
    private int priority;
    private int cumulativePriority;
    private Map<String, Integer> allocatedCategories; // category as key and number of allocated ones as value
    private List<String> slotPreferences; // Fetch the slots ordered by pref.
    private Map<String, List<String>> coursePreferences; // Fetch the courses ordered by its pref in each slot.


    private List<String> slotPrefAfterAllocation;
    private Map<String, List<String>> coursePrefAfterAllocation;


    public AllocationStudent(String sid, String name, String program, int semester, Map<String, Integer> requirements, List<String> slotPreferences, Map<String, List<String>> coursePreferences) {
        this.sid = sid;
        this.name = name;
        this.program = program;
        this.semester = semester;
        this.requirements = requirements;
        this.allocatedCourses = new HashSet<>();
        this.allocatedSlots = new HashSet<>();
        this.priority = 1;
        this.cumulativePriority = 0;
        this.allocatedCategories = new HashMap<>();
        this.slotPreferences=slotPreferences;
        this.coursePreferences=coursePreferences;
    }

    public AllocationStudent(String sid, String name, String program, int semester) {
        this.sid = sid;
        this.name = name;
        this.program = program;
        this.semester = semester;
        this.allocatedCourses = new HashSet<>();
        this.allocatedSlots = new HashSet<>();
        this.priority = 1;
        this.cumulativePriority = 0;
        this.allocatedCategories = new HashMap<>();
    }

    public void addAllocatedCourse(String course){
        allocatedCourses.add(course);
    }
    public void addAllocatedSlot(String slot){
        allocatedSlots.add(slot);
    }
    public void addAllocatedCategory(String category, int cnt){
        allocatedCategories.put(category,cnt);
    }
}
