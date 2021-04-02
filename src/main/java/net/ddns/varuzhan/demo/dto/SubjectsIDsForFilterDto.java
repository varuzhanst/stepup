package net.ddns.varuzhan.demo.dto;

public class SubjectsIDsForFilterDto implements Comparable {

    private String filterId;
    private String filterName;

    public SubjectsIDsForFilterDto(String filterId, String filterName) {
        this.filterId = filterId;
        this.filterName = filterName;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    @Override
    public int compareTo(Object o) {
        return this.filterId.compareTo(((SubjectsIDsForFilterDto) o).filterId);
    }
}
