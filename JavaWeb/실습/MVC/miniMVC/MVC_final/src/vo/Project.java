package vo;

import java.util.Date;

public class Project {
  protected int     id; //프로젝트일련번호
  protected String  title; //프로젝트명
  protected String  contents; //설명
  protected Date    start; //시작일
  protected Date    end; //종료일
  protected int     state; //상태
  protected Date    created_at; //생성일
  protected String  tags; //태그들
  
  public int getId() {
    return id;
  }
  public Project setId(int id) {
    this.id = id;
    return this;
  }
  public String getTitle() {
    return title;
  }
  public Project setTitle(String title) {
    this.title = title;
    return this;
  }
  public String getContents() {
    return contents;
  }
  public Project setContents(String contents) {
    this.contents = contents;
    return this;
  }
  public Date getStart() {
    return start;
  }
  public Project setStart(Date start) {
    this.start = start;
    return this;
  }
  public Date getEnd() {
    return end;
  }
  public Project setEnd(Date end) {
    this.end = end;
    return this;
  }
  public int getState() {
    return state;
  }
  public Project setState(int state) {
    this.state = state;
    return this;
  }
  public Date getCreatedAt() {
    return created_at;
  }
  public Project setCreatedAt(Date created_at) {
    this.created_at = created_at;
    return this;
  }
  public String getTags() {
    return tags;
  }
  public Project setTags(String tags) {
    this.tags = tags;
    return this;
  }
}
