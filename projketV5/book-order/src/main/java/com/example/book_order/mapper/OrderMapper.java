package com.example.book_order.mapper;
import com.example.book_order.model.Order;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface OrderMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantity", ignore = true)
Order toEntity(Order order);

Order toDetails(Order order);
}
