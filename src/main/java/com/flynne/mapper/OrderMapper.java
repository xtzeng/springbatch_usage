package com.flynne.mapper;

/**
 * @author xiaoti
 * @date 2024/10/27 21:46
 */
import com.flynne.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insert(Order order);

    Order selectById(@Param("id") Long id);

    List<Order> selectAll();

    int update(Order order);

    int deleteById(@Param("id") Long id);
}
