package models;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

public class Period {

    public Date fromTime;
    public Date toTime;

    public Period(Date fromTime, Date toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
