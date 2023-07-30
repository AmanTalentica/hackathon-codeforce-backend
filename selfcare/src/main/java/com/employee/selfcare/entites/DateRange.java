package com.employee.selfcare.entites;


import java.util.Date;

public class DateRange {
    private Date startDate;
    private Date endDate;

    public DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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

    public boolean isValid() {
        // Check if the start date is before or equal to the end date
        return startDate != null && endDate != null && !endDate.before(startDate);
    }

    public DateRange() {
		
	}

	@Override
    public String toString() {
        return "DateRange{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
