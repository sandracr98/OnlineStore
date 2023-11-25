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

        itemsNumberPerPage = 5;
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
