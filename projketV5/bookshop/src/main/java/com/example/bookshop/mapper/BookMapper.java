package com.example.bookshop.mapper;



import com.example.bookshop.model.Book;
import com.example.bookshop.model.BookCreateRequest;
import com.example.bookshop.model.BookDetails;
import com.example.bookshop.model.BookToOrderDetails;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "author", ignore = true)
    Book toEntity(BookCreateRequest bookCreateRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    BookDetails toDetails(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "author", ignore = true)
    void updateEntity(BookCreateRequest bookCreateRequest, @MappingTarget Book book);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    BookToOrderDetails toOrderDetails(Book book);
}


