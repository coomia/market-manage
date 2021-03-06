package me.jiangcai.logistics.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * 描述货品性质、规格的参数
 * Created by helloztt on 2017/9/12.
 */
@Setter
@Getter
@Entity
@ToString
public class PropertyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 参数值
     */
    @Column(length = 60)
    private String value;
    /**
     * 属性
     */
    @ManyToOne
    private PropertyName propertyName;
    /**
     * 默认图标
     */
    @Column(length = 60)
    private String icon;
    /**
     * 排序
     */
    private int weight;
    /**
     * 是否无效
     */
    private boolean disabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyValue)) return false;
        PropertyValue propertyValue = (PropertyValue) o;
        return Objects.equals(id, propertyValue.getId());
    }
}
