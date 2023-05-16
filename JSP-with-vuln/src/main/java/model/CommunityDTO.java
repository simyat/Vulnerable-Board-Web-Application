package model;

public class CommunityDTO {
    private int id;
    private String user_id;
    private String name;
    private String title;
    private String content;
    private String postdate;
    private String original_file;
    private String save_file;
    private int visit_count;
    private int like_count;

    public CommunityDTO() {
    }
    
    public CommunityDTO(int id, String user_id, String name, String title, String content, String postdate,
            String original_file, String save_file, int visit_count, int like_count) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.postdate = postdate;
        this.original_file = original_file;
        this.save_file = save_file;
        this.visit_count = visit_count;
        this.like_count = like_count;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPostdate() {
        return postdate;
    }
    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }
    public String getOriginal_file() {
        return original_file;
    }
    public void setOriginal_file(String original_file) {
        this.original_file = original_file;
    }
    public String getSave_file() {
        return save_file;
    }
    public void setSave_file(String save_file) {
        this.save_file = save_file;
    }
    public int getVisit_count() {
        return visit_count;
    }
    public void setVisit_count(int visit_count) {
        this.visit_count = visit_count;
    }
    public int getLike_count() {
        return like_count;
    }
    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
