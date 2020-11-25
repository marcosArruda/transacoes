package br.com.marcos.transacoes.api.resources;

import java.util.Arrays;
import java.util.StringJoiner;

public enum OperationType {

    BUYOUT_CASH(1, "COMPRA_A_VISTA", true),
    BUYOUT_SPLIT(2, "COMPRA_PARCELADA", true),
    WITHDRAW(3, "SAQUE", true),
    PAYMENT(4, "PAGAMENTO", false);

    private final int id;
    private final String name;
    private final boolean negative;

    OperationType(final int id, final String name, final boolean negative){
        this.id = id;
        this.name = name;
        this.negative = negative;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isNegative() {
        return negative;
    }

    public static OperationType fromId(int id){
        for (OperationType o : OperationType.values()){
            if(o.getId() == id){
                return o;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", OperationType.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("negative=" + negative)
                .toString();
    }
}
