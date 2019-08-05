package com.tvo.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 *
 * @author: hungpd
 * @version: 1.0
 */
public class PageResultOrigin<T> {
    private final Page<T> page;
    private final List<PageItem> items;
    private final int currentNumber;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Handling getTotalItem, Items current page, last page, first page..
     *
     * @param page
     *            Page include total page, current page
     * @param url
     *            Link page
     */
    public PageResultOrigin(final Page<T> page, final String url) {
        this.page = page;
        this.url = url;
        items = new ArrayList<PageItem>();

        currentNumber = page.getNumber() + 1;

        int start, size;
        if (page.getTotalPages() <= AppConstant.MAX_PAGE_ITEM_DISPLAY) {
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= AppConstant.MAX_PAGE_ITEM_DISPLAY - AppConstant.MAX_PAGE_ITEM_DISPLAY / 2) {
                start = 1;
                size = AppConstant.MAX_PAGE_ITEM_DISPLAY;
            } else if (currentNumber >= page.getTotalPages() - AppConstant.MAX_PAGE_ITEM_DISPLAY / 2) {
                start = page.getTotalPages() - AppConstant.MAX_PAGE_ITEM_DISPLAY + 1;
                size = AppConstant.MAX_PAGE_ITEM_DISPLAY;
            } else {
                start = currentNumber - AppConstant.MAX_PAGE_ITEM_DISPLAY / 2;
                size = AppConstant.MAX_PAGE_ITEM_DISPLAY;
            }
        }

        for (int i = 0; i < size; i++) {
            items.add(new PageItem(start + i, (start + i) == currentNumber));
        }
    }

    public List<PageItem> getItems() {
        return items;
    }

    public int getNumber() {
        return currentNumber;
    }

    public List<T> getContent() {
        return page.getContent();
    }

    public int getSize() {
        return page.getSize();
    }

    public int getCurrentPage() {
        return page.getPageable().getPageNumber();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public boolean isFirstPage() {
        return page.isFirst();
    }

    public boolean isLastPage() {
        return page.isLast();
    }

    public boolean isHasPreviousPage() {
        return page.hasPrevious();
    }

    public boolean isHasNextPage() {
        return page.hasNext();
    }

    /**
     * This is page item class. Get number page current. Check it have to current page
     *
     * @author: hungpd
     * @version: 1.0
     */
    public class PageItem {
        private final int number;
        private final boolean current;

        public PageItem(final int number, final boolean current) {
            this.number = number;
            this.current = current;
        }

        public int getNumber() {
            return this.number;
        }

        public boolean isCurrent() {
            return this.current;
        }
    }
}