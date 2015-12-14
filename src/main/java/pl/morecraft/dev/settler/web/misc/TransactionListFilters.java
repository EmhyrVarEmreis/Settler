package pl.morecraft.dev.settler.web.misc;

public class TransactionListFilters {

    private String owner;
    private String reference;
    private Double valueGt;
    private Double valueLt;
    private Double value;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getValueGt() {
        return valueGt;
    }

    public void setValueGt(Double valueGt) {
        this.valueGt = valueGt;
    }

    public Double getValueLt() {
        return valueLt;
    }

    public void setValueLt(Double valueLt) {
        this.valueLt = valueLt;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
