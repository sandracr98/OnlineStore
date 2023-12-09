package com.sandrajavaschool.OnlineStore.paginator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageRender<T> {

    //Designed to assist with rendering pagination for a given page of items

    // Attributes to store URL, Page object, and pagination details

    private String url;
    private Page<T> page;

    private int totalPages;
    private int itemsNumberPerPage;
    private  int actualPage;
    private List<PageItem> pages;


    // Constructor taking URL and Page as parameters
    public PageRender(String url, Page<T> page) {

        this.url = url;
        this.page = page;
        this.pages = new ArrayList<PageItem>();

        // Default values for items per page
        itemsNumberPerPage = 5;

        // Calculating total pages, actual page, and initializing from/to values for page range
        totalPages = page.getTotalPages();
        actualPage = page.getNumber() + 1;

        int from, to;

        // Condition to determine range of displayed page numbers

        if (totalPages <= itemsNumberPerPage) {
            from = 1;
            to = totalPages;
        }else {
            if (actualPage <= itemsNumberPerPage / 2) {
                from = 1;
                to = itemsNumberPerPage;
            } else if (actualPage >= totalPages - itemsNumberPerPage / 2) {
                from = totalPages - itemsNumberPerPage + 1;
                to = itemsNumberPerPage;
            } else {
                from = actualPage - itemsNumberPerPage / 2;
                to = itemsNumberPerPage;
            }
        }

        // Loop to generate PageItems and add them to the 'pages' list
        for (int i = 0; i < to; i++) {
            pages.add(new PageItem(from + i, actualPage == from + i));
        }

    }

    // Methods to check if the current page is the first, last, has next, or has previous
    public boolean isFirst() {
        return page.isFirst();
    }
    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }



}
