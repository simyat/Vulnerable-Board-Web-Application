package model;

public class SearchDTO {
    private String keywords; // 검색 내용
    private String currentSearchBy; // 검색 필터
    private String SearchFromDate; // 시작 날짜
    private String SearchToDate; // 종료 날짜
    private String currentSearchOrdeyBy; // 정렬

    public SearchDTO() {
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSearchFromDate() {
        return SearchFromDate;
    }

    public void setSearchFromDate(String searchFromDate) {
        SearchFromDate = searchFromDate;
    }

    public String getSearchToDate() {
        return SearchToDate;
    }

    public void setSearchToDate(String searchToDate) {
        SearchToDate = searchToDate;
    }

    public String getCurrentSearchBy() {
        return currentSearchBy;
    }

    public void setCurrentSearchBy(String currentSearchBy) {
        this.currentSearchBy = currentSearchBy;
    }

    public String getCurrentSearchOrdeyBy() {
        return currentSearchOrdeyBy;
    }

    public void setCurrentSearchOrdeyBy(String currentSearchOrdeyBy) {
        this.currentSearchOrdeyBy = currentSearchOrdeyBy;
    }

}
