package datastructure;

import java.util.Comparator;
import java.util.Objects;

public class MyItem implements Comparable<MyItem>{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    private String name;

    private int cost;

    private int profit;

    public MyItem(String name, int cost, int profit) {
        this.name = name;
        this.cost = cost;
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyItem)) return false;
        MyItem myItem = (MyItem) o;
        return getCost() == myItem.getCost() &&
                getProfit() == myItem.getProfit() &&
                Objects.equals(getName(), myItem.getName());
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", profit=" + profit +
                '}';
    }

    @Override
    public int compareTo(MyItem myItem) {
        // 注意是Class::xxx
        // 多字段比较，其中第二个是逆序
        return Comparator.comparing(MyItem::getCost)
                .thenComparing(MyItem::getProfit, Comparator.reverseOrder())
                .thenComparing(MyItem::getName)
                .compare(this, myItem);
    }

}
