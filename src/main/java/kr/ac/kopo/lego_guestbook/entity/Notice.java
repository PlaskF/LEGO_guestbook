package kr.ac.kopo.lego_guestbook.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 공지사항 ID (Primary Key)

    @Column(nullable = false, length = 100)
    private String title;  // 공지사항 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 공지사항 내용

    @Column(nullable = false)
    private String author;  // 작성자

    @Column(nullable = false)
    private LocalDateTime createdAt;  // 작성일

    @Column(nullable = false)
    private Long views = 0L;  // 조회수

    // 기본 생성자
    public Notice() {}

    // 필드 생성자
    public Notice(String title, String content, String author, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.views = 0L;
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

    // 조회수 증가 메서드
    public void incrementViews() {
        this.views += 1;
    }
}