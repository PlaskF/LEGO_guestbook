package kr.ac.kopo.lego_guestbook.dto;

import java.time.LocalDateTime;

public class NoticeDTO {

    private Long id;  // 공지사항 ID
    private String title;  // 공지사항 제목
    private String content;  // 공지사항 내용
    private String author;  // 작성자
    private LocalDateTime createdAt;  // 작성일
    private Long views;  // 조회수

    // 기본 생성자
    public NoticeDTO() {}

    // 필드 생성자
    public NoticeDTO(Long id, String title, String content, String author, LocalDateTime createdAt, Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.views = views;
    }

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}