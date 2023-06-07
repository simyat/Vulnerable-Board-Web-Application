package model;

public class SearchDTO {
    private String keywords; // 검색 내용
    private String currentSearchBy; // 검색 필터
    private String currentSearchDate; // 날짜
    private String currentSearchOrdeyBy; // 정렬
    
    public SearchDTO() {
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCurrentSearchBy() {
        return currentSearchBy;
    }

    public void setCurrentSearchBy(String currentSearchBy) {
        this.currentSearchBy = currentSearchBy;
    }

    public String getCurrentSearchDate() {
        return currentSearchDate;
    }

    public void setCurrentSearchDate(String currentSearchDate) {
        this.currentSearchDate = currentSearchDate;
    }

    public String getCurrentSearchOrdeyBy() {
        return currentSearchOrdeyBy;
    }

    public void setCurrentSearchOrdeyBy(String currentSearchOrdeyBy) {
        this.currentSearchOrdeyBy = currentSearchOrdeyBy;
    }

    
}
