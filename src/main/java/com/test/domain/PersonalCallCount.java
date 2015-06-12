package com.test.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

@Entity
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "PERSONAL_CALLCOUNT")
public class PersonalCallCount {


    @Id
    @Column(name = "EMPLOYEE_ID")
    private Long id;
    @Column(name = "EMPLOYEE_CALL_COUNT")
    @NotNull
    private long count;
    @Column(name = "EMPLOYEE_LAST_CALL")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public PersonalCallCount() {

    }

    public PersonalCallCount(Long id) {
        this.id = id;

    }

    public PersonalCallCount(Long id, long count, Date date) {
        this.id = id;
        this.count = count;
        this.date = date;
    }

    public PersonalCallCount(Long id, long count) {
        this.id = id;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", Call_Count=" + count + ", Last_call=" + date + "]";
    }

}
