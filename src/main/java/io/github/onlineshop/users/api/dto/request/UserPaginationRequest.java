package io.github.onlineshop.users.api.dto.request;

import jakarta.validation.constraints.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

public record UserPaginationRequest(
    @RequestParam(required = false)
    @NotNull
    @Positive(message = "Page number must be positive")
    Integer pageNumber,

    @RequestParam(required = false)
    @NotNull
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    Integer pageSize,

    @RequestParam(required = false)
    @NotNull
    @Pattern(
        regexp = "id|username|registrationDate|role",
        message =
            "Sort field must be one of: id, username, registrationDate, role"
    )
    String sortBy,

    @RequestParam(required = false)
    @NotNull
    @Pattern(
        regexp = "ascending|descending",
        message = "Sort direction must be either 'ascending' or 'descending'"
    )
    String sortDirection
) {
    private static final int PAGENUMBER_DEFAULT = 1;
    private static final int PAGESIZE_DEFAULT = 100;
    private static final String SORTBY_DEFAULT = "id";
    private static final String SORTDIRECTION_DEFAULT = "ascending";

    public UserPaginationRequest {
        if(pageNumber == null)
            pageNumber = PAGENUMBER_DEFAULT;

        if(pageSize == null)
            pageSize = PAGESIZE_DEFAULT;

        if(sortBy == null)
            sortBy = SORTBY_DEFAULT;

        if(sortDirection == null)
            sortDirection = SORTDIRECTION_DEFAULT;
    }

    public Pageable toPageable() {
        Sort sort;

        if(sortDirection.equalsIgnoreCase(SORTDIRECTION_DEFAULT))
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }
}